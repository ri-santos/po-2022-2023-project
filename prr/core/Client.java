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
  private ArrayList<Notification> _notifications;


  public Client(String key, String name, int taxNumber){
    _key = key;
    _name = name;
    _taxNumber = taxNumber;
    _clientLevel = ClientLevel.NORMAL;
    _recieveNotifications = true;
    _terminals = new TreeMap<String, Terminal>();
    _payments = 0;
    _debts = 0;
    _notifications = new ArrayList<Notification>();
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

  public ArrayList<Notification> getNotifications(){
    return _notifications;
  }

  public String toString(){
    return ("CLIENT|" + _key + "|" + _name + "|" + _taxNumber + "|" + 
    _clientLevel + "|" + (_recieveNotifications? "YES":"NO") + "|" + _terminals.size() + "|" +
    (long)_payments + "|" + (long)_debts);
  }
}

