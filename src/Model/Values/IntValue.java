package Model.Values;

import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value
{
    private final int val;

    public IntValue(int val)
    {
        this.val = val;
    }

    public int getValue()
    {
        return val;
    }

    @Override
    public Type getType()
    {
        return new IntType();
    }

    @Override
    public String toString()
    {
        return Integer.toString(val);
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof IntValue))
        {
            return false;
        }
        return this.val == ((IntValue)o).getValue();
    }

    @Override
    public Value deepCopy()
    {
        return new IntValue(val);
    }

}
