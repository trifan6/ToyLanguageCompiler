package Model.ADT;

import java.util.Map;

public interface MyIDictionary<K, V>
{
    void put(K key, V value);

    boolean isDefined(K key);

    V get(K key);

    void update(K key, V value);

    void remove(K key);

    MyIDictionary<K, V> deepCopy();

    Map<K, V> getAll();
}
