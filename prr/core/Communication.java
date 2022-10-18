package prr.core;

import java.io.Serializable;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Communication implements Serializable{

    private static final long serialVersionUID = 202208091753L;

    private int _id;
    private boolean _isPaid;
    private double _cost;
    private boolean _isOngoing;
    private Terminal _from;
    private Terminal _to;
    private String CommunicationType;

    public Communication(int id , Terminal from , Terminal to){
        _id = id;
        _isPaid = false;
        _cost = 0;
        _isOngoing = false;
        _from = from;
        _to = to;
    }
    public int getCommunicationId(){
        return _id;
    }

    public boolean isPaid(){
        return _isPaid;
    }

    public double getCost(){
        return _cost;
    }

    public boolean isOngoing(){
        return _isOngoing;
    }

}