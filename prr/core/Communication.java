package prr.core;

import java.io.Serializable;


/**
 * Abstract terminal.
 */
abstract public class Communication implements Serializable{

    private static final long serialVersionUID = 202208091753L;

    private int _id;
    private boolean _isPaid;
    protected double _cost;
    private Terminal _from;
    private Terminal _to;
    private String CommunicationType;

    public Communication(int id , Terminal from , Terminal to){
        _id = id;
        _isPaid = false;
        _cost = 0;
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


    abstract public void computeCost();

    abstract public int getSize();
}