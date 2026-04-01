package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyITable;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt
{
    private final Exp exp;

    public CloseRFileStmt(Exp exp)
    {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        Value v = exp.eval(state.getSymTable(), state.getHeap());
        if (!(v instanceof StringValue))
            throw new MyException("closeRFile: expression is not a string.");
        StringValue sVal = (StringValue) v;

        MyITable ft = state.getFileTable();
        if (!ft.isDefined(sVal))
            throw new MyException("closeRFile: file " + sVal.getVal() + " is not opened.");

        BufferedReader br = ft.get(sVal.toString());
        try
        {
            br.close();
        }
        catch (IOException e)
        {
            throw new MyException("closeRFile: IO error while closing file " + sVal.getVal() + ": " + e.getMessage());
        }
        ft.remove(sVal.toString());
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException
    {
        Type expType = exp.typecheck(typeEnv);
        if (expType.equals(new StringType()))
        {
            return typeEnv;
        }
        else
        {
            throw new MyException("CloseRFile: Expression must be of type String");
        }
    }

    @Override
    public String toString()
    {
        return "closeRFile(" + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy()
    {
        return new CloseRFileStmt(exp.deepCopy());
    }
}
