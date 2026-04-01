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

public class WriteHeapStmt implements IStmt
{
    private final String varName;
    private final Exp exp;

    public WriteHeapStmt(String varName, Exp exp)
    {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if(!symTable.isDefined(varName))
        {
            throw new MyException("wH: variable"+varName+" is not defined");
        }

        Value varValue = symTable.get(varName);
        if(!(varValue instanceof RefValue))
        {
            throw new MyException("wH: variable"+varName+" is not a ref value");
        }

        int address = ((RefValue)varValue).getAddress();
        if(!heap.isDefined(address))
        {
            throw new MyException("wH: address is not defined");
        }

        Value evaluated = exp.eval(symTable, heap);

        Type locType = ((RefValue)varValue).getLocationType();
        if(!evaluated.getType().equals(locType))
        {
            throw new MyException("wH: type of expression " + evaluated.getType().toString() + " does not match location type" + locType.toString());
        }

        heap.update(address, evaluated);
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
            throw new MyException("WriteHeap: right hand side and left hand side have different types");
    }

    @Override
    public String toString()
    {
        return "wH(" + varName + ", " + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy()
    {
        return new WriteHeapStmt(varName, exp.deepCopy());
    }
}
