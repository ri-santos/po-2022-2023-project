package prr.core.TerminalState;

import prr.core.exception.DestinationTerminalIsBusyException;

public class BusyMode extends TerminalMode {
    @Override
  public void acceptSMS(){}
  
  @Override
  public void acceptInteractive() throws DestinationTerminalIsBusyException{
    throw new DestinationTerminalIsBusyException();
  }

  @Override
  public boolean makeComm(){return false;}

  @Override
  public String toString(){return "BUSY";}
}
