package prr.core;

public class TextCommunication extends Communication{

    private String _message;
    
    public TextCommunication(String message,int id , Terminal from , Terminal to){
        super(id,from,to);
        _message = message;
    }
}