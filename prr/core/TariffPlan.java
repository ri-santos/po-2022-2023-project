package prr.core;

import java.io.Serializable;

public class TariffPlan implements Serializable{
    
    private static final long serialVersionUID = 202208091753L;

    private String _name;

    public TariffPlan(String name){
        _name = name;
    }

}
