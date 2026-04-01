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
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt
{
    private final Exp exp;

    public OpenRFileStmt(Exp exp)
    {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        Value v = exp.eval(state.getSymTable(), state.getHeap());
        if (!(v instanceof StringValue)) {
            throw new MyException("OpenRFile: expression is not a string)");
        }
        StringValue svalue = (StringValue) v;
        MyITable ft = state.getFileTable();

        if (ft.isDefined(svalue)) {
            throw new MyException("OpenRFile:" + svalue.toString() + " already opened");
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(svalue.getVal()));
            ft.put(svalue.getVal(), br);
        } catch (IOException e) {
            throw new MyException("OpenRFile: IO error: " + svalue.getVal() + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException
    {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("File statement requires a string expression");
    }

    @Override
    public String toString()
    {
        return "OpenRFile: " + exp.toString();
    }

    @Override
    public IStmt deepCopy()
    {
        return new OpenRFileStmt(exp.deepCopy());
    }
}
