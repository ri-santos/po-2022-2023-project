package prr.core.exception;

public class DuplicateKeyException extends Exception{
    private final String _key;
    public DuplicateKeyException(String key){
        super();
        _key = key;
    }
    public String getKey(){
        return _key;
    }
}