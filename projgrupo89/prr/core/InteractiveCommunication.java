package prr.core;

abstract public class InteractiveCommunication extends Communication{
    int _duration;

    public InteractiveCommunication(int duration , int id , Terminal from , Terminal to){
        super(id , from , to);
        _duration = duration; 
    }
}