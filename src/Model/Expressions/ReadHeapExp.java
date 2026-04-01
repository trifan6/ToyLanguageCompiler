package Model.Expressions;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class ReadHeapExp implements Exp
{
    private final Exp exp;

    public ReadHeapExp(Exp exp)
    {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> dict, MyIHeap<Integer, Value> heap) throws MyException
    {
        Value val = exp.eval(dict, heap);
        if(!(val instanceof RefValue))
        {
            throw new MyException("rH: expression is not a RefValue");
        }

        int address = ((RefValue)val).getAddress();
        if(!heap.isDefined(address))
        {
            throw new MyException("rH: address is not defined");
        }
        return heap.get(address);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException
    {
        Type typ = exp.typecheck(typeEnv);
        if(typ instanceof RefType)
        {
            RefType refType = (RefType)typ;
            return refType.getInner();
        }
        else
        {
            throw new MyException("rH: type is not a RefType");
        }
    }

    @Override
    public String toString()
    {
        return "rH(" + exp.toString() + ")";
    }

    @Override
    public Exp deepCopy()
    {
        return new ReadHeapExp(exp.deepCopy());
    }
}
