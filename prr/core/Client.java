package prr.core;

import java.util.Collection;

public class Client {
    
    private String _key;
    private String _name;
    private int _taxNumber;
    private ClientLevel _clientLevel;
    private boolean _recieveNotifications;
    private Collection<Terminal> _terminals;
    private TariffPlan _tariff;


    public Client(String key, String name, int taxNumber){
        _key = key;
        _name = name;
        _taxNumber = taxNumber;
        _clientLevel = ClientLevel.NORMAL;

    }
}
