package Model.ADT;

import Exceptions.ADTException;
import Exceptions.MyException;
import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap implements MyIHeap<Integer, Value>
{
    private final Map<Integer, Value> heap;
    private int freeLocation = 1;

    public MyHeap()
    {
        heap = new HashMap<>();
    }

    @Override
    public synchronized int allocate(Value value)
    {
        int address = freeLocation;
        heap.put(address, value);
        freeLocation++;
        while (heap.containsKey(freeLocation)) freeLocation++;
        return address;
    }

    @Override
    public Value get(Integer address) throws MyException
    {
        if (!heap.containsKey(address))
            throw new ADTException("Heap: address " + address + " not found");
        return heap.get(address);
    }

    @Override
    public boolean isDefined(Integer address)
    {
        return heap.containsKey(address);
    }

    @Override
    public void update(Integer address, Value value) throws MyException
    {
        if (!heap.containsKey(address))
            throw new MyException("Heap: address " + address + " not found");
        heap.put(address, value);
    }

    @Override
    public void remove(Integer address) throws MyException
    {
        if (!heap.containsKey(address))
            throw new MyException("Heap: address " + address + " not found");
        heap.remove(address);
    }

    @Override
    public Map<Integer, Value> getContent()
    {
        return heap;
    }

    @Override
    public void setContent(Map<Integer, Value> newMap)
    {
        heap.clear();
        heap.putAll(newMap);
        freeLocation = 1;
        while (heap.containsKey(freeLocation)) freeLocation++;
    }

    @Override
    public Set<Integer> keySet()
    {
        return heap.keySet();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Value> e : heap.entrySet()) {
            sb.append(e.getKey()).append(" -> ").append(e.getValue()).append("\n");
        }
        return sb.toString();
    }
}
