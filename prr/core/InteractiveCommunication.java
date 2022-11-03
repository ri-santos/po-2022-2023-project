package prr.core;

abstract public class InteractiveCommunication extends Communication{
    int _duration;

    public InteractiveCommunication(int id, Terminal from, Terminal to){
        super(id , from , to);
        _isOngoing = true;
    }

    public int getSize(){
        return _duration;
    }
}