package prr.core;

public class VideoCommunication extends InteractiveCommunication{
  public VideoCommunication(int id , Terminal from , Terminal to){
    super(id, from, to);
  }

  @Override
  public double computeCost(){
    double cost = getFrom().getOwner().getClientLevel().videoCost(_size);
    if(getFrom().isFriend(getIdReceiver())){cost /= 2;}
    _cost = cost;
    return cost;
  }

  @Override
  public String toString(){
    return "VIDEO|" + super.toString();
  }
}