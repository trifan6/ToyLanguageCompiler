package Model;

import Exceptions.MyException;
import Model.ADT.*;
import Model.Statements.IStmt;
import Model.Values.Value;

import java.io.BufferedReader;

public class PrgState
{
    private static int nextId = 0;
    private int id;

    private final MyIStack<IStmt> exeStack;
    private final MyIDictionary<String, Value> symTable;
    private final MyIList<Value> out;
    private MyITable fileTable;
    private MyIHeap<Integer, Value> heap;
    private MyIBarrierTable barrierTable;
    private IStmt originalProgram;

    public static synchronized int getNewId()
    {
        return nextId++;
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> out, MyITable fileTable, MyIHeap<Integer, Value> heap, IStmt originalProgram, MyIBarrierTable barrierTable)
    {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = originalProgram.deepCopy();
        this.id = getNewId();
        this.exeStack.push(originalProgram);
        this.barrierTable = barrierTable;
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> out, MyITable fileTable, MyIHeap<Integer, Value> heap, MyIBarrierTable barrierTable)
    {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = getNewId();
        this.barrierTable = barrierTable;
    }

    public MyIBarrierTable getBarrierTable() {
        return barrierTable;
    }
    public int getId()
    {
        return id;
    }

    public MyIStack<IStmt> getExeStack()
    {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable()
    {
        return symTable;
    }

    public MyIList<Value> getOut()
    {
        return out;
    }

    public MyITable getFileTable()
    {
        return fileTable;
    }

    public MyIHeap<Integer, Value> getHeap()
    {
        return heap;
    }

    public void setFileTable(MyITable table)
    {
        this.fileTable = table;
    }

    public void setHeap(MyIHeap<Integer, Value> heap)
    {
        this.heap = heap;
    }

    public Boolean isNotCompleted()
    {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException
    {
        if(exeStack.isEmpty())
        {
            throw new MyException("PrgState: exeStack is empty");
        }

        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    @Override
    public String toString()
    {
        return "Id: " + id + "\n" + "ExeStack: " + exeStack.toString() + "\n" + "SymTable: " + symTable.toString() + "\n" + "Out: " + out.toString() + "\n" + "FileTable: " + fileTable.toString() + "\n" + "Heap: " + heap.toString() + "\nBarrierTable: " + barrierTable.toString() + "-----------------------------\n";
    }

}
