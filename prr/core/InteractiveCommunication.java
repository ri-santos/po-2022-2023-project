package prr.core;

abstract public class InteractiveCommunication extends Communication{
    private boolean _isOngoing;
    int _duration;

    public InteractiveCommunication(int id, Terminal from, Terminal to){
        super(id , from , to);
        _isOngoing = true;
    }

    public int getSize(){
        return _duration;
    }
    
    public boolean getCommState(){
        return _isOngoing;
    }
}