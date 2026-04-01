package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.ADT.MyStack;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class ForkStmt implements IStmt
{
    private IStmt stmt;

    public ForkStmt(IStmt stmt)
    {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState prgState) throws MyException
    {
        MyIStack<IStmt> newStack = new MyStack<>();
        MyIDictionary<String, Value> newSymTable = prgState.getSymTable().deepCopy();
        return new PrgState(newStack, newSymTable, prgState.getOut(), prgState.getFileTable(), prgState.getHeap(), stmt, prgState.getBarrierTable());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException
    {
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public IStmt deepCopy()
    {
        return new ForkStmt(stmt.deepCopy());
    }

    @Override
    public String toString()
    {
        return "fork(" + stmt.toString() + ")";
    }
}
