package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;

public class RefValue implements Value
{
    private final int address;
    private final Type locationType;

    public RefValue(int address, Type locationType)
    {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress()
    {
        return address;
    }

    public Type getLocationType()
    {
        return locationType;
    }

    @Override
    public Type getType()
    {
        return new RefType(locationType);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof RefValue) {
            RefValue other = (RefValue) o;
            return this.address == other.address && this.locationType.equals(other.locationType);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return Integer.hashCode(address) ^ locationType.hashCode();
    }

    @Override
    public String toString()
    {
        return "("  + address + ", " + locationType.toString() + ")";
    }

    @Override
    public Value deepCopy()
    {
        return new RefValue(address, locationType.deepCopy());
    }
}
