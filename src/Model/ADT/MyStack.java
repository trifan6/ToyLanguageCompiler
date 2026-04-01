package Model.ADT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>
{
    private final Stack<T> stack;

    public MyStack()
    {
        this.stack = new Stack<>();
    }

    @Override
    public void push(T v)
    {
        this.stack.push(v);
    }

    @Override
    public T pop()
    {
        if(this.stack.isEmpty())
        {
            System.out.println("Stack is empty");
            return null;
        }
        return this.stack.pop();
    }

    @Override
    public boolean isEmpty()
    {
        return this.stack.isEmpty();
    }

    @Override
    public T peek()
    {
        if(this.stack.isEmpty()) return  null;
        return this.stack.peek();
    }

    @Override
    public List<T> getAll()
    {
        return new ArrayList<>(this.stack);
    }

    @Override
    public String toString()
    {
        String str = "[";
        for(int i = stack.size() - 1; i >= 0; i--)
        {
            str = str + this.stack.get(i).toString();
            if (i>0) str = str + ", ";
        }
        str = str + "]";
        return str;
    }

    @Override
    public List<T> getReversed() {
        List<T> list = new ArrayList<>(stack);
        Collections.reverse(list);
        return list;
    }
}
