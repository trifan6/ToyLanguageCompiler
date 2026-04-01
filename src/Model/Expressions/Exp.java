package Model.Expressions;

import Model.ADT.MyIDictionary;
import Exceptions.MyException;
import Model.ADT.MyIHeap;
import Model.Types.Type;
import Model.Values.Value;

public interface Exp
{
    //Value eval(MyIDictionary<String, Value> dict) throws MyException;

    Value eval(MyIDictionary<String, Value> dict, MyIHeap<Integer, Value> heap) throws MyException;

    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;

    Exp deepCopy();
}
