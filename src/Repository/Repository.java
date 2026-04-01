package Repository;

import Exceptions.MyException;
import Model.ADT.MyIStack;
import Model.PrgState;
import Model.Statements.IStmt;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository
{
    private final List<PrgState> prgStates;
    private final String logFilePath;

    public Repository()
    {
        prgStates = new ArrayList<>();
        this.logFilePath = "";
    }

    public Repository(PrgState prgState, String logFilePath)
    {
        this.prgStates = new ArrayList<>();
        this.prgStates.add(prgState);
        this.logFilePath = logFilePath;
    }

    @Override
    public List<PrgState> getPrgList()
    {
        return prgStates;
    }

    @Override
    public void setPrgList(List<PrgState> prgState)
    {
        this.prgStates.clear();
        this.prgStates.addAll(prgState);
    }

    @Override
    public void logPrgStateExec(PrgState state) throws MyException
    {
        try
        {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(
                    new FileWriter(logFilePath, true)));

            logFile.println("Id: "  + state.getId());

            logFile.println("ExeStack:");
            MyIStack<IStmt> stack = state.getExeStack();
            List<IStmt> stackContent = stack.getReversed();
            for(IStmt stmt: stackContent)
            {
                logFile.println(stmt.toString());
            }

            logFile.println("SymTable");
            for(String key : state.getSymTable().getAll().keySet())
            {
                Value val = state.getSymTable().get(key);
                logFile.println(key + ": " + val.toString());
            }

            logFile.println("Out:");
            for(Value v : state.getOut().getAll())
            {
                logFile.println(v.toString());
            }

            logFile.println("FileTable:");
            for (String key : state.getFileTable().getAll().keySet()) {
                logFile.println(key);
            }

            logFile.println("Heap:");
            for (Integer key : state.getHeap().keySet())
            {
                logFile.println(key + " -> " + state.getHeap().get(key));
            }

            logFile.println("----------------------------------------");
            logFile.close();
        }
        catch (IOException e)
        {
            throw new MyException("Error writing file: " + e.getMessage());
        }
    }
}
