package prr.core;

public class VoiceCommunication extends InteractiveCommunication{
 
  public VoiceCommunication(int id , Terminal from , Terminal to){
    super(id, from, to);
  }


  @Override
  public double computeCost(){
    double cost = getFrom().getOwner().getClientLevel().voiceCost(_size);
    if(getFrom().isFriend(getIdReceiver())){cost /= 2;}
    _cost = cost;
    return cost;
  }

  @Override
  public String toString(){
    return "VOICE|" + super.toString();
  }
}