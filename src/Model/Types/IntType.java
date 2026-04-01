package Model.Types;

import Model.Values.IntValue;
import Model.Values.Value;

public class IntType implements Type
{
    @Override
    public String toString()
    {
        return "int";
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof IntType;
    }

    @Override
    public Type deepCopy()
    {
        return new IntType();
    }

    @Override
    public Value defaultValue()
    {
        return new IntValue(0);
    }
}
