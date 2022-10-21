package prr.core.exception;

public class TerminalDoesNotExistException extends Exception {
    private String _id;
    
    public TerminalDoesNotExistException(String id){
        super();
        _id = id;
    }
    
    public String getId(){
        return _id;
    }
}
