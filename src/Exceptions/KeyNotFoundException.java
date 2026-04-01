package Exceptions;

public class KeyNotFoundException extends ADTException
{
    public KeyNotFoundException(String key)
    {
        super("Key " + key + " not found!");
    }
}
