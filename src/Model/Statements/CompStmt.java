package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.PrgState;
import Model.Types.Type;

public class CompStmt implements IStmt
{
    private final IStmt first;
    private final IStmt second;

    public CompStmt(IStmt first, IStmt second)
    {
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state)
    {
        MyIStack<IStmt> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException
    {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString()
    {
        return "(" + first + "," + second + ")";
    }

    @Override
    public IStmt deepCopy()
    {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }
}
