package prr.core.TerminalState;

import prr.core.exception.DestinationTerminalisSilentException;

public class SilenceMode extends TerminalMode{
  @Override
  public void acceptSMS(){}
  @Override
  public void acceptInteractive() throws DestinationTerminalisSilentException{
    throw new DestinationTerminalisSilentException();
  }

  @Override
  public boolean makeComm(){return true;}

  @Override
  public String toString(){return "SILENCE";}
}
