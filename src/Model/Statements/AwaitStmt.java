package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIBarrierTable;
import Model.ADT.MyIDictionary;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import javafx.util.Pair;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt {
    private final String var;
    private static final Lock lock = new ReentrantLock();

    public AwaitStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        try {
            MyIDictionary<String, Value> symTable = state.getSymTable();
            MyIBarrierTable barrierTable = state.getBarrierTable();

            if (!symTable.isDefined(var))
                throw new MyException("Await: Variable not defined!");
            Value val = symTable.get(var);
            if (!val.getType().equals(new IntType()))
                throw new MyException("Await: Variable is not int!");

            int foundIndex = ((IntValue) val).getValue();

            if (!barrierTable.containsKey(foundIndex))
                throw new MyException("Await: Barrier not found at index " + foundIndex);

            Pair<Integer, List<Integer>> barrierData = barrierTable.get(foundIndex);
            int N1 = barrierData.getKey();
            List<Integer> list1 = barrierData.getValue();
            int NL = list1.size();

            if (N1 > NL) {
                if (list1.contains(state.getId())) {
                    state.getExeStack().push(this);
                } else {
                    list1.add(state.getId());
                    state.getExeStack().push(this);
                }
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.get(var).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("Await: Var must be int!");
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(var);
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }
}