package Model.ADT;

import Exceptions.MyException;
import Model.Values.Value;

import java.util.Map;
import java.util.Set;

public interface MyIHeap<K, V>
{
    int allocate(V value);

    V get(K key) throws MyException;

    boolean isDefined(K key) throws MyException;

    void update(K key, V value) throws MyException;

    void remove(K key) throws MyException;

    Map<K, V> getContent();

    void setContent(Map<K, V> newMap);

    Set<K> keySet();
}
