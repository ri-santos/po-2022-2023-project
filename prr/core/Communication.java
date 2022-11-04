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
  protected boolean _isOngoing;
  protected int _size;

  public Communication(int id , Terminal from , Terminal to){
    _id = id;
    _isPaid = false;
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

  public String getIdSender(){
    return _from.getId();
  }

  public String getIdReceiver(){
    return _to.getId();
  }

  public Terminal getFrom(){
    return _from;
  }

  public void setnotOngoing(){
    _isOngoing = false;
  }

  public void setSize(int duration){
    _size = duration;
  }

  public boolean getCommState(){
    return _isOngoing;
  }

  public String toString(){
    return getCommunicationId() + "|" + getIdSender() + "|" + getIdReceiver() + "|" +
    _size + "|" + Math.round(getCost()) + "|" + (_isOngoing? "ONGOING" : "FINISHED");
  }
  
  abstract public double computeCost();

    
}