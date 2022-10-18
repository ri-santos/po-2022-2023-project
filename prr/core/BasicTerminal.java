package prr.core;

public class BasicTerminal extends Terminal{

    public BasicTerminal(String id, Client owner){
        super(id, owner);
        super.setTerminalType("BASIC");
    }

    public String toString(){
        return super.toString();
    }



    public void makeVideoCall(Terminal to){}

    protected void acceptVideoCall(Terminal from){}
}
