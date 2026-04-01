package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.PrgState;
import Model.Types.Type;

public class NopStmt implements IStmt
{
    @Override
    public PrgState execute(PrgState state)
    {
        return state;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException
    {
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return null;
    }

    @Override
    public IStmt deepCopy()
    {
        return new NopStmt();
    }
}
