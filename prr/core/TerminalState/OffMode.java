package prr.core.TerminalState;

import prr.core.exception.DestinationTerminalIsOffException;

public class OffMode extends TerminalMode {
    
  @Override
  public void acceptSMS() throws DestinationTerminalIsOffException{
    throw new DestinationTerminalIsOffException();
  }
  @Override
  public void acceptInteractive() throws DestinationTerminalIsOffException{
    throw new DestinationTerminalIsOffException();
  }

  @Override
  public boolean makeComm(){return false;}
  
  @Override
  public String toString(){return "OFF";}
}
