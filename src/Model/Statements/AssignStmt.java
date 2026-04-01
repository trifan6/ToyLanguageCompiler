package Model.Statements;

import Exceptions.KeyNotFoundException;
import Model.ADT.MyIDictionary;
import Exceptions.MyException;
import Model.Expressions.Exp;
import Model.Types.Type;
import Model.Values.Value;
import Model.PrgState;


public class AssignStmt implements IStmt
{
    private final String id;
    private final Exp exp;

    public AssignStmt(String id, Exp exp)
    {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIDictionary<String, Value> symTable =  state.getSymTable();

        if(!symTable.isDefined(id))
        {
            throw new KeyNotFoundException(id);
        }

        Value val = exp.eval(symTable, state.getHeap());
        Value varVal = symTable.get(id);

        if(!val.getType().equals(varVal.getType()))
        {
            throw new MyException("Type mismatch is assignment to " + id);
        }

        symTable.update(id, val);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException
    {
        Type typevar = typeEnv.get(id);
        Type typeexp = exp.typecheck(typeEnv);

        if (typevar.equals(typeexp)) {
            return typeEnv;
        } else {
            throw new MyException("Type mismatch is assignment to " + id);
        }
    }

    @Override
    public String toString()
    {
        return id + "=" + exp;
    }

    @Override
    public IStmt deepCopy()
    {
        return new AssignStmt(id, exp.deepCopy());
    }
}
