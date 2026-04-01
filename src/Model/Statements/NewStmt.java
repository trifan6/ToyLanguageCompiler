package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class NewStmt implements IStmt
{
    private final String varName;
    private final Exp exp;

    public NewStmt(String varName, Exp exp)
    {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if(!symTable.isDefined(this.varName))
        {
            throw new MyException("Variable " + this.varName + " is not defined");
        }

        Value varValue = symTable.get(this.varName);
        if(!(varValue.getType() instanceof RefType))
        {
            throw new MyException("Variable " + this.varName + " is not of type RefType");
        }

        RefType refType = (RefType) varValue.getType();
        Value evaluated  = exp.eval(symTable, heap);

        if(!evaluated.getType().equals(refType.getInner()))
        {
            throw new MyException("new: type of expression " + evaluated.getType() + " does not match location type " + refType.getInner());
        }

        int newAddress = heap.allocate(evaluated);

        symTable.put(this.varName, new RefValue(newAddress, refType.getInner()));

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException
    {
        Type typevar = typeEnv.get(varName);
        Type typeexp = exp.typecheck(typeEnv);

        if (typevar.equals(new RefType(typeexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types");
    }

    @Override
    public String toString()
    {
        return "new("  + this.varName + ", " + this.exp + ")";
    }

    @Override
    public IStmt deepCopy()
    {
        return new NewStmt(this.varName, this.exp.deepCopy());
    }
}
