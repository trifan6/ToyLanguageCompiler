package Model.Expressions;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Types.Type;
import Model.Values.Value;

public class ValueExp implements Exp
{
    private final Value val;

    public ValueExp(Value val)
    {
        this.val = val;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> dict, MyIHeap<Integer, Value> heap)
    {
        return this.val;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException
    {
        return val.getType();
    }

    @Override
    public String toString()
    {
        return this.val.toString();
    }

    @Override
    public Exp deepCopy()
    {
        return new ValueExp(val.deepCopy());
    }
}
