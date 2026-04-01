package Model.Statements;

import Model.ADT.MyIDictionary;
import Model.ADT.MyIList;
import Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class PrintStmt implements IStmt
{
    private final Exp exp;

    public PrintStmt(Exp exp)
    {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIList<Value> out = state.getOut();
        MyIDictionary<String, Value> symTable = state.getSymTable();

        Value val = exp.eval(symTable, state.getHeap());
        out.add(val);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException
    {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return "print(" + exp + ")";
    }

    @Override
    public IStmt deepCopy()
    {
        return new PrintStmt(exp.deepCopy());
    }
}

