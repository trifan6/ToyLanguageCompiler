package Exceptions;

public class EmptyExecutionStackException extends StatementException
{
    public EmptyExecutionStackException()
    {
        super("Empty Execution Stack! Cannot execute further");
    }
}
