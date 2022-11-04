package prr.core;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import prr.core.TerminalState.BusyMode;
import prr.core.TerminalState.IdleMode;
import prr.core.TerminalState.OffMode;
import prr.core.TerminalState.SilenceMode;
import prr.core.TerminalState.TerminalMode;
import prr.core.exception.DestinationTerminalIsBusyException;
import prr.core.exception.DestinationTerminalIsOffException;
import prr.core.exception.DestinationTerminalisSilentException;
import prr.core.exception.UnsupportedAtDestinationException;
import prr.core.exception.UnsupportedAtOriginException;

import java.util.Collection;


/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  private String _id;
  protected Client _owner;
  private double _debt;
  private double _payments;
  private TerminalMode _mode;
  private TerminalMode _lastmode;
  private TreeMap<String, Terminal> _friends;
  private Set<Client> _toNotify;
  private Communication _onGoingCommunication;
  private TreeMap<Integer, Communication> _madeCommunications;
  private TreeMap<Integer, Communication> _receivedCommunications;

  
  public Terminal(String id, Client owner){
    _id = id;
    _owner = owner;
    _mode = new IdleMode();
    _friends = new TreeMap<String, Terminal>();
    _toNotify = new HashSet<Client>();
    _madeCommunications = new TreeMap<Integer, Communication>();
    _receivedCommunications = new TreeMap<Integer, Communication>();
  }

  public String getId(){
    return _id;
  }

  public Client getOwner(){
    return _owner;
  }

  public TerminalMode getMode(){
    return _mode;
  }

  public Double getDebts(){
    return _debt;
  }

  public Double getPayments(){
    return _payments;
  }

  public double getBalance(){
    return _payments - _debt;
  }

  public TerminalMode getTerminalType(){
    return _mode;
  }

  public Collection<Communication> getMadeCommunications(){
    return _madeCommunications.values();
  }

  public Collection<Communication> getReceivedCommunications(){
    return _receivedCommunications.values();
  }
  
  public Communication getOngoingCommunication(){
    return _onGoingCommunication;
  }

  public HashSet<String> showAllCommunications(){
    HashSet<String> communications = new HashSet<String>();
    for (Communication communication : getReceivedCommunications()){
      communications.add(communication.toString());
    }
    for (Communication communication : getMadeCommunications()){
      communications.add(communication.toString());
    }
    return communications;
  }

  public int numberOfCommunications(){
    return (_madeCommunications.size() + _receivedCommunications.size());
  }

  public void setOnSilent(){
    if(_onGoingCommunication == null){
        _lastmode = _mode;
        _mode = new SilenceMode();
        if (_lastmode.toString().equals("OFF")){notifyClients();}
    }
  }

  public void setOnIdle(){
    if(_onGoingCommunication == null){
        _lastmode = _mode;
        _mode = new IdleMode();
        notifyClients();
    }
  }

  public void turnOff(){
    if(_onGoingCommunication == null){
        _lastmode = _mode;
        _mode = new OffMode();
    }
  }

  public void setBusy(){
    _lastmode = _mode;
    _mode = new BusyMode();
  }

  public void endOngoingCommunicationFrom(){
    _debt += _onGoingCommunication.computeCost();
    endOngoingCommunicationTo();
    _owner.verifyClientLevelChange();
  }

  public void endOngoingCommunicationTo(){
    _onGoingCommunication._isOngoing = false;
    _onGoingCommunication = null;
    _mode = _lastmode;
    _lastmode = new BusyMode();
  }

  public void setOngoingCommunication(Communication newCommunication){
    _onGoingCommunication = newCommunication;
    setBusy();
  }

  public boolean hasOngoingCommunication(){
    return this.getOngoingCommunication() != null;
  }
  
  public void addNewFriend(Terminal friend, String id){
    _friends.put(id, friend);
  }

  public void removeFriend(String id){
    _friends.remove(id);
  }

  public boolean isFriend(String id){
    return _friends.containsKey(id);
  }

  public void addNotifyClient(Client client){
    _toNotify.add(client);
  }

  public void notifyClients(){
    String type = (_lastmode.toString() + "to" + _mode.toString());
    Notification notification = new Notification(type, this);
    _toNotify.forEach(client -> client.update(notification));
    _toNotify.clear();
  }

  public boolean doPayment(int key){
    Communication comm = _madeCommunications.get(key);
    if (comm.getFrom() == this){
      double price = comm.getCost();
      _payments += price;
      _debt -= price;
      verifyClientLevelChange(_owner);
      return true;
    }
    return false;
  }

  public void verifyClientLevelChange(Client c){
    c.setPayments();
    c.setDebts();
    c.getClientLevel().upgradeLevel(c);
  }

  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication(){
    return (_onGoingCommunication != null && _onGoingCommunication.getFrom() == this);
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    return _mode.makeComm();
  }


  public String toString(){
    String terminalString = "|" + getId() + "|" + getOwner().getKey() + "|" + _mode + "|" + 
    (long)_payments + "|" + (long)_debt;

    if (_friends.size() != 0){
      String friendIds = String.join(",",_friends.keySet());
      terminalString += "|" + friendIds;
    }

    return terminalString;
  }

  public void addMadeCommunication(Communication comm){
    _madeCommunications.put(comm.getCommunicationId(), comm);
  }

  public void addReceivedCommunication(Communication comm){
    _receivedCommunications.put(comm.getCommunicationId(), comm);
  }

  public void makeSMS(Terminal to, Communication comm) throws DestinationTerminalIsOffException{
    to._mode.acceptSMS();
    addMadeCommunication(comm);
    _debt += comm.computeCost();
    to.addReceivedCommunication(comm);
    _owner.updateHistory(1);
    _owner.verifyClientLevelChange();
  }

  public void makeVoiceCall(Terminal to, Communication comm) throws DestinationTerminalIsOffException, DestinationTerminalisSilentException, DestinationTerminalIsBusyException{
    _owner.updateHistory(2);
    makeInteractiveCommunication(to, comm);
  }

  public void makeInteractiveCommunication(Terminal to, Communication comm) throws DestinationTerminalIsOffException, DestinationTerminalisSilentException, DestinationTerminalIsBusyException{
    setOngoingCommunication(comm);
    addMadeCommunication(comm);
    to.setOngoingCommunication(comm);
    to.addReceivedCommunication(comm);
  }

  public abstract void makeVideoCall(Terminal to, Communication comm) throws UnsupportedAtDestinationException, UnsupportedAtOriginException, DestinationTerminalIsOffException, DestinationTerminalisSilentException, DestinationTerminalIsBusyException;

  public abstract void acceptVideoCall(Communication comm) throws UnsupportedAtDestinationException;

}