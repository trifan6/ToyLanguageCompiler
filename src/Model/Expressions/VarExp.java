package Model.Expressions;

import Model.ADT.MyIDictionary;
import Exceptions.MyException;
import Model.ADT.MyIHeap;
import Model.Types.Type;
import Model.Values.Value;

public class VarExp implements Exp
{
    private final String id;

    public VarExp(String id)
    {
        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> dict, MyIHeap<Integer, Value> heap) throws MyException
    {
        if(!dict.isDefined(this.id))
        {
            throw new MyException("Variable " + this.id + " is not defined");
        }
        return dict.get(this.id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException
    {
        return typeEnv.get(id);
    }

    @Override
    public String toString()
    {
        return this.id;
    }

    @Override
    public Exp deepCopy()
    {
        return new VarExp(id);
    }
}
