package Exceptions;

public class ADTException extends MyException
{
    public ADTException(String message)
    {
        super("ADT Exception:" + message);
    }
}
