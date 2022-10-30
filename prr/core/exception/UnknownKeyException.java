package prr.core.exception;

public class UnknownKeyException extends Exception{
    public final String _key;

    public UnknownKeyException(String key){
        super();
        _key = key;
    }

    public String getKey(){
        return _key;
    }

}