package prr.core;

public class VoiceCommunication extends InteractiveCommunication{
 
  public VoiceCommunication(int duration , int id , Terminal from , Terminal to){
    super(id, to, from);
  }

  @Override
  public void computeCost(){}
}