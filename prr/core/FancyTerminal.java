package prr.core;

import prr.core.exception.DestinationTerminalIsBusyException;
import prr.core.exception.DestinationTerminalIsOffException;
import prr.core.exception.DestinationTerminalisSilentException;
import prr.core.exception.UnsupportedAtDestinationException;

public class FancyTerminal extends Terminal {
    
    public FancyTerminal(String id, Client owner){
        super(id, owner);
    }

    public String toString(){
        return "FANCY" + super.toString();
    }

    @Override
    public void makeVideoCall(Terminal to, Communication comm) throws DestinationTerminalIsOffException, DestinationTerminalisSilentException, DestinationTerminalIsBusyException, UnsupportedAtDestinationException{
        makeVoiceCall(to, comm);
    }
        

    public void acceptVideoCall(Communication comm){
        setOngoingCommunication(comm);
        addReceivedCommunication(comm);
    }
}
