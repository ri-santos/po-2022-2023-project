package prr.core;

import java.io.Serializable;
import java.util.*;

public class Client implements Serializable{
    
  private static final long serialVersionUID = 202208091753L;
  
  private String _key;
  private String _name;
  private int _taxNumber;
  private ClientLevel _clientLevel;
  private boolean _recieveNotifications;
  private TreeMap<String, Terminal> _terminals;
  private double _payments;
  private double _debts;
  private List<DeliveryType> _inbox;
  private int[] _history;


  public Client(String key, String name, int taxNumber){
    _key = key;
    _name = name;
    _taxNumber = taxNumber;
    _clientLevel = new ClientLevelNormal();
    _recieveNotifications = true;
    _terminals = new TreeMap<String, Terminal>();
    _payments = 0;
    _debts = 0;
    _inbox = new ArrayList<DeliveryType>();
    _history = new int[5];
  }

  public String getKey(){
    return _key;
  }

  public String getName(){
    return _name;
  }

  public int getTaxNumber(){
    return _taxNumber;
  }

  public ClientLevel getClientLevel(){
    return _clientLevel;
  }

  public void addClientTerminal(Terminal terminal, String id){
    _terminals.put(id, terminal);
  }

  public void turnNotificationsOn(){
    _recieveNotifications = true;
  }

  public void turnNotificationsOff(){
    _recieveNotifications = false;
  }

  public boolean getRecieveNotificationsState(){
    return _recieveNotifications;
  }
  public double getDebts(){
    return _debts;
  }

  public void setLevel(ClientLevel newLevel){
    _clientLevel = newLevel;
    clearHistory();
  }

  public double getPayments(){
    return _payments;
  }

  public void clearInbox(){
    _inbox.clear();
  }

  public void setPayments(){
    _terminals.values().forEach(term -> _payments += term.getDebts());
  }

  public void setDebts(){
    _terminals.values().forEach(term -> _debts += term.getDebts());
  }

  public List<DeliveryType> getInbox(){
    return _inbox;
  }
  
  public void update(DeliveryType mail){
    if(_recieveNotifications){_inbox.add(mail);}
  }

  public List<Communication> getMadeCommunications(){
    List<Communication> madeComms = new ArrayList<>();
    _terminals.values().forEach(t -> madeComms.addAll(t.getMadeCommunications()));
    return madeComms;
  }

  public Collection<Communication> getReceivedCommunications(){
    Collection<Communication> receivedComms = new ArrayList<>();
    _terminals.values().forEach(t -> receivedComms.addAll(t.getReceivedCommunications()));
    return receivedComms;
  }

  public void updateHistory(int last){
    int i = 0;
    while (i < 5){
      if (_history[i] == 0){
        _history[i] = last;
        return;
      }
      else{i++;}
    }
    i = 0;
    while (i < 4){
      _history[i] = _history[i++];
    }
    _history[i] = last;
  }

  public void clearHistory(){
    _history = new int[5];
  }

  public boolean verifyLast2Text(){
    int i = 0;
    while (i <= 5){
      if (i == 5 | _history[i] == 0){
        if (_history[i--] == 1 && _history[i--] == 1){
          return true;
        }
        else{return false;}
      }
      else {i++;}
    }
  return false;

  }

  public boolean verifyLast5Video(){
    int i = 0;
    while (i < 5){
      if (_history[i] != 3){
        return false;
      }
      else {i++;}
    }
  return true;

  }

  public String toString(){
    return ("CLIENT|" + _key + "|" + _name + "|" + _taxNumber + "|" + 
    _clientLevel.toString() + "|" + (_recieveNotifications? "YES":"NO") + "|" + _terminals.size() + "|" +
    Math.round(_payments) + "|" + Math.round(_debts));
  }
}

