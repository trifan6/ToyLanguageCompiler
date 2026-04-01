package Model.ADT;

import Exceptions.MyException;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class MyTable implements MyITable
{
    private final Map<String, BufferedReader> fileTable;

    public MyTable()
    {
        this.fileTable = new HashMap<>();
    }

    @Override
    public void put(String key, BufferedReader value) throws MyException
    {
        if (this.fileTable.containsKey(key)) {
            throw new MyException("Duplicate key found");
        }
        this.fileTable.put(key, value);
    }

    @Override
    public boolean isDefined(StringValue key)
    {
        return this.fileTable.containsKey(key.toString());
    }

    @Override
    public BufferedReader get(String key) throws MyException
    {
        if (!this.fileTable.containsKey(key))
        {
            throw new MyException("Key not found");
        }
        return this.fileTable.get(key);
    }

    @Override
    public Map<String, BufferedReader> getAll()
    {
        return this.fileTable;
    }

    @Override
    public void remove(String key) throws MyException
    {
        if(!this.fileTable.containsKey(key))
        {
            throw new MyException("Key not found");
        }
        this.fileTable.remove(key);
    }

    @Override
    public String toString()
    {
        return this.fileTable.toString();
    }
}
