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
    private String _notificationsOnOff;
    private HashSet<Terminal> _terminals;
    private TariffPlan _tariff;
    private double _payments;
    private double _debts;
    private List<Notification> _notifications;


    public Client(String key, String name, int taxNumber){
        _key = key;
        _name = name;
        _taxNumber = taxNumber;
        _clientLevel = ClientLevel.NORMAL;
        _recieveNotifications = true;
        _notificationsOnOff = "YES";
        _terminals = new HashSet<Terminal>();
        _tariff = new TariffPlan("Base");
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

    public void addClientTerminal(Terminal terminal){
        _terminals.add(terminal);
    }

    public String toString(){
        return ("CLIENT|" + _key + "|" + _name + "|" + _taxNumber + "|" + 
        _clientLevel + "|" + _notificationsOnOff + "|" + _terminals.size() + "|" +
        (long)_payments + "|" + (long)_debts);
    }
}
