package prr.core;

public class TextCommunication extends Communication{

  private String _message;
  
  public TextCommunication(String message, int id, Terminal from, Terminal to){
    super(id,from,to);
    _message = message;
    _size = message.length();
  }

  @Override
  public double computeCost(){
    double cost = getFrom().getOwner().getClientLevel().textCost(_size);
    _cost = cost;
    return cost;
  }

  @Override
  public String toString(){
    return "TEXT|" + super.toString();
  }
}