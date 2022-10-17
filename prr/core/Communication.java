package prr.core;

import java.io.Serializable;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Communication implements Serializable{
    private int _id;
    private boolean _isPaid;
    private double _cost;
    private boolean _isOngoing;
    private Terminal _from;
    private Terminal _to;

    public Communication(int id , Terminal from , Terminal to){
        _id = id;
        _isPaid = false;
        _cost = 0;
        _isOngoing = false;
        _form = form;
        _to = to;
    }
    

}