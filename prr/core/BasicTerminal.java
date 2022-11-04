package prr.core;

import prr.core.exception.UnsupportedAtDestinationException;
import prr.core.exception.UnsupportedAtOriginException;

public class BasicTerminal extends Terminal{

    public BasicTerminal(String id, Client owner){
        super(id, owner);
    }

    public String toString(){
        return "BASIC" + super.toString();
    }

    public void makeVideoCall(Terminal to, Communication comm) throws UnsupportedAtOriginException{
        throw new UnsupportedAtOriginException();
    }

    @Override
    public void acceptVideoCall(Communication comm) throws UnsupportedAtDestinationException {
        throw new UnsupportedAtDestinationException();
    }
}
