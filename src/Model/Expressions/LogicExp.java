package Model.Expressions;

import Model.ADT.MyIDictionary;
import Exceptions.MyException;
import Model.ADT.MyIHeap;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExp implements Exp
{
    private final Exp e1;
    private final Exp e2;
    private final int op;

    public LogicExp(int op, Exp e1, Exp e2)
    {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException
    {
        Value v1 = e1.eval(tbl, heap);
        if (!v1.getType().equals(new BoolType()))
            throw new MyException("First operand is not a boolean");

        Value v2 = e2.eval(tbl, heap);
        if (!v2.getType().equals(new BoolType()))
            throw new MyException("Second operand is not a boolean");

        boolean b1 = ((BoolValue) v1).getValue();
        boolean b2 = ((BoolValue) v2).getValue();

        return switch (op)
        {
            case 1 -> new BoolValue(b1 && b2);
            case 2 -> new BoolValue(b1 || b2);
            default -> throw new MyException("Unknown logical operator");
        };
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException
    {
        Type type1 = e1.typecheck(typeEnv);
        Type type2 = e2.typecheck(typeEnv);

        if(type1.equals(new BoolType()))
        {
            if(type2.equals(new BoolType()))
                return new BoolType();
            else
                throw new MyException("Second operand is not a boolean");
        }
        else
        {
            throw new MyException("First operand is not a boolean");
        }
    }

    @Override
    public String toString()
    {
        String opStr = (op == 1) ? "and" : "or";
        return "(" + e1.toString() + " " + opStr + " " + e2.toString() + ")";
    }

    @Override
    public Exp deepCopy()
    {
        return new LogicExp(op, e1.deepCopy(), e2.deepCopy());
    }
}
