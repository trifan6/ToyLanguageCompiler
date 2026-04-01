package Model.Values;

import Model.Types.StringType;
import Model.Types.Type;


public class StringValue implements Value
{
    private final String val;

    public StringValue(String val)
    {
        this.val = val;
    }

    public String getVal()
    {
        return val;
    }
    @Override
    public Type getType()
    {
        return new StringType();
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof StringValue))
        {
            return false;
        }
        return val.equals(((StringValue)o).getVal());
    }

    @Override
    public String toString()
    {
        return val;
    }

    @Override
    public Value deepCopy()
    {
        return new StringValue(val);
    }
}
