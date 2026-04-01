package Model.Statements;

import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class IfStmt implements IStmt
{
    private final Exp exp;
    private final IStmt thenStmt;
    private final IStmt elseStmt;

    public IfStmt(Exp exp, IStmt thenStmt, IStmt elseStmt)
    {
        this.exp = exp;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public PrgState execute (PrgState state) throws MyException
    {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, Value> symTable = state.getSymTable();

        Value cond = exp.eval(symTable, state.getHeap());
        if(!cond.getType().equals(new BoolType()))
        {
            throw new MyException("Condition is not boolean");
        }
        if(((BoolValue)cond).getValue())
            stack.push(thenStmt);
        else
            stack.push(elseStmt);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException
    {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenStmt.typecheck(typeEnv.deepCopy());
            elseStmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new MyException("The condition of IF has not the type bool");
        }
    }

    @Override
    public String toString()
    {
        return "if(" + exp + ") then(" + thenStmt + ") else(" + elseStmt + ")";
    }

    @Override
    public IStmt deepCopy()
    {
        return new IfStmt(exp.deepCopy(), thenStmt.deepCopy(), elseStmt.deepCopy());
    }
}
