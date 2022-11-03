package prr.core;

public class FancyTerminal extends Terminal {
    
    public FancyTerminal(String id, Client owner){
        super(id, owner);
    }

    public String toString(){
        return "FANCY" + super.toString();
    }

    @Override
    public void makeVideoCall(Terminal to, Communication comm){}

    public void acceptVideoCall(){}
}
