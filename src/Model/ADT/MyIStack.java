package Model.ADT;

import java.util.List;

public interface MyIStack<T>
{
    void push(T v);

    T pop();

    boolean isEmpty();

    List<T> getAll();

    T peek();

    List<T> getReversed();

}
