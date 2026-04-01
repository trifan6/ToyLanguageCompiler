package Model.Expressions;

import Model.ADT.MyIDictionary;
import Exceptions.MyException;
import Model.ADT.MyIHeap;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class ArithExp implements Exp
{
    private final Exp e1;
    private final Exp e2;
    private final String op;

    public ArithExp(String  op, Exp e1, Exp e2)
    {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException
    {
        Value v1 = e1.eval(tbl, heap);
        if (!v1.getType().equals(new IntType()))
            throw new MyException("First operand is not an integer");

        Value v2 = e2.eval(tbl, heap);
        if (!v2.getType().equals(new IntType()))
            throw new MyException("Second operand is not an integer");

        int n1 = ((IntValue) v1).getValue();
        int n2 = ((IntValue) v2).getValue();

        switch (op)
        {
            case "+" : return new IntValue(n1 + n2);
            case "-": return new IntValue(n1 - n2);
            case "*": return new IntValue(n1 * n2);
            case "/":
                if (n2 == 0) throw new MyException("Division by zero");
                return new IntValue(n1 / n2);
            default: throw new MyException("Unknown arithmetic operator");
        }
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException
    {
        Type type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);

        if(type1.equals(new IntType()))
        {
            if(type2.equals(new IntType()))
            {
                return new IntType();
            }
            else
            {
                throw new MyException("Second operand is not an integer");
            }
        }
        else
        {
            throw new MyException("First operand is not an integer");
        }
    }

    @Override
    public String toString()
    {

        return "(" + e1.toString() + " " + op + " " + e2.toString() + ")";
    }

    @Override
    public Exp deepCopy()
    {
        return new ArithExp(op, e1.deepCopy(), e2.deepCopy());
    }
}