package prr.core.TerminalState;

import java.io.Serializable;

import prr.core.exception.DestinationTerminalIsBusyException;
import prr.core.exception.DestinationTerminalIsOffException;
import prr.core.exception.DestinationTerminalisSilentException;

public abstract class TerminalMode implements Serializable{

    private static final long serialVersionUID = 202208091753L;

    public abstract void acceptSMS() throws DestinationTerminalIsOffException;
    public abstract void acceptInteractive() throws DestinationTerminalIsBusyException, DestinationTerminalIsOffException, DestinationTerminalisSilentException;
    public abstract boolean makeComm();
    public abstract String toString();

}
