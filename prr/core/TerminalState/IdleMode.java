package prr.core.TerminalState;

public class IdleMode extends TerminalMode{
    
  @Override
  public void acceptSMS(){}
  @Override
  public void acceptInteractive(){}
  @Override
  public boolean makeComm(){return true;}

  @Override
  public String toString(){return "IDLE";}
}
