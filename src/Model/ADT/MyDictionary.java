package Model.ADT;

import Model.PrgState;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V>
{
    private final Map<K, V> dict;

    public MyDictionary()
    {
        this.dict = new HashMap<>();
    }

    @Override
    public void put(K key, V value)
    {
        this.dict.put(key, value);
    }

    @Override
    public boolean isDefined(K key)
    {
        return this.dict.containsKey(key);
    }

    @Override
    public V get(K key)
    {
        return this.dict.get(key);
    }

    @Override
    public void update(K key, V value)
    {
        if (!isDefined(key))
        {
            System.out.println("key not found, inserting...");
        }
        this.dict.put(key, value);
    }

    @Override
    public void remove(K key)
    {
        this.dict.remove(key);
    }

    @Override
    public Map<K, V> getAll()
    {
        return this.dict;
    }

    @Override
    public String toString()
    {
        return this.dict.toString();
    }

    @Override
    public MyIDictionary<K, V> deepCopy()
    {
        MyIDictionary<K, V> toReturn = new MyDictionary<K, V>();

        for (K key : this.dict.keySet())
        {
            toReturn.put(key, this.dict.get(key));
        }
        return toReturn;
    }
}
