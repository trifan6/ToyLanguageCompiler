package Model.Types;

import Model.Values.StringValue;
import Model.Values.Value;

public class StringType implements Type
{
    @Override
    public String toString()
    {
        return "string";
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof StringType;
    }

    @Override
    public Type deepCopy()
    {
        return new StringType();
    }

    @Override
    public Value defaultValue()
    {
        return new StringValue("");
    }
}
