package prr.core;

import java.io.Serializable;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Collection;


/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  private String _id;
  private Client _owner;
  private double _debt;
  private double _payments;
  private TerminalMode _mode;
  private String _terminalType;
  private HashMap<String, Terminal> _friends;
  private HashMap<String, Client> _toNotify;
  private Communication _ongoingCommunication;
  private HashMap<Integer, Communication> _madeCommunications;
  private HashMap<Integer, Communication> _receivedCommunications;

  
  public Terminal(String id, Client owner){
    _id = id;
    _owner = owner;
    _mode = TerminalMode.IDLE;
    _debt = 0;
    _payments = 0;
    _friends = new HashMap<String, Terminal>();
    _toNotify = new HashMap<String, Client>();
    _madeCommunications = new HashMap<Integer, Communication>();
    _receivedCommunications = new HashMap<Integer, Communication>();
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

  public String getTerminalType(){
    return _terminalType;
  }

  public void setTerminalType(String type){
    _terminalType = type;
  }

  public String toString(){
    return (_terminalType + "|" + getId() + "|" + getOwner().getKey() + "|" + _mode + "|" + 
    (long)_payments + "|" + (long)_debt);
  }

  public Collection<Communication> getMadeCommunications(){
    return _madeCommunications.values();
  }

  public Collection<Communication> getRecievedCommunications(){
    return _receivedCommunications.values();
  }

  public HashSet<String> showAllCommunications(){
    HashSet<String> communications = new HashSet<String>();
    for (Communication communication : getRecievedCommunications()){
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
    _mode = TerminalMode.SILENCE;
  }

  public void setOnIdle(){
    _mode = TerminalMode.IDLE;
  }

  public void turnOff(){
    _mode = TerminalMode.OFF;
  }

  public void addNewFriend(Terminal friend, String id){
    _friends.put(id, friend);
  }

  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    return (_mode == TerminalMode.BUSY);
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    return (_mode != TerminalMode.OFF | _mode != TerminalMode.BUSY);
  }
}

