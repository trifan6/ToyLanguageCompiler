package Model.Types;

import Model.Values.RefValue;
import Model.Values.Value;

public class RefType implements Type
{
    private final Type inner;

    public RefType(Type inner)
    {
        this.inner = inner;
    }

    public Type getInner()
    {
        return this.inner;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof RefType)
        {
            return this.inner.equals(((RefType)o).getInner());
        }
        return false;
    }

    @Override
    public String toString()
    {
        return "Ref("+this.inner.toString()+")";
    }

    @Override
    public Value defaultValue()
    {
        return new RefValue(0, inner);
    }

    @Override
    public Type deepCopy()
    {
        return new RefType(inner.deepCopy());
    }

}
