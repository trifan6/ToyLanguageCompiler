package Exceptions;

public class EmptyStackException extends ADTException
{
    public EmptyStackException()
    {
        super("Tried popping form an empty stack!");
    }
}
