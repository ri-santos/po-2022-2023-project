package prr.core.exception;

public class InvalidTerminalIdException extends Exception {

    private final String _id;

    public InvalidTerminalIdException(String id){
        super();
        _id = id;
    }

    public String getId(){
        return _id;
    }

}