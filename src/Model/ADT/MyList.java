package Model.ADT;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T>
{
    private final List<T> list;

    public MyList()
    {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T value)
    {
        this.list.add(value);
    }

    @Override
    public List<T> getAll()
    {
        return this.list;
    }

    @Override
    public String toString()
    {
        return this.list.toString();
    }
}
