package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value
{
    private final boolean val;

    public BoolValue(boolean val)
    {
        this.val = val;
    }

    public boolean getValue()
    {
        return val;
    }

    @Override
    public Type getType()
    {
        return new BoolType();
    }

    @Override
    public String toString()
    {
        return Boolean.toString(val);
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof BoolValue))
        {
            return false;
        }
        return this.val == ((BoolValue)o).getValue();
    }

    @Override
    public Value deepCopy()
    {
        return new BoolValue(val);
    }
}
