package Exceptions;

public class ExpressionException extends MyException
{
    public ExpressionException(String message)
    {
        super("Expression error: " + message);
    }
}
