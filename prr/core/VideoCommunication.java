package prr.core;

public class VideoCommunication extends InteractiveCommunication{
    public VideoCommunication(int duration , int id , Terminal from , Terminal to){
        super(id, from, to);
    }
  
    @Override
    public void computeCost(){}
}