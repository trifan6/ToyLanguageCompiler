package Model.Types;

import Model.Values.Value;

public interface Type
{
    String toString();

    boolean equals(Object o);

    Type deepCopy();

    Value defaultValue();
}
