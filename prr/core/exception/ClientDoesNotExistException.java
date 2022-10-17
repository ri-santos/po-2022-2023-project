package prr.core.exception;

public class ClientDoesNotExistException extends Exception{
    public final String _key;

    public ClientDoesNotExistException(String key){
        super();
        _key = key;
    }
    
    public String getKey(){
        return _key;
    }

}
