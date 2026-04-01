package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIBarrierTable;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewBarrierStmt implements IStmt {
    private final String var;
    private final Exp exp;
    private static final Lock lock = new ReentrantLock();

    public NewBarrierStmt(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        try {
            MyIDictionary<String, Value> symTable = state.getSymTable();
            MyIHeap<Integer, Value> heap = state.getHeap();
            MyIBarrierTable barrierTable = state.getBarrierTable();

            Value number = exp.eval(symTable, heap);
            if (!number.getType().equals(new IntType())) {
                throw new MyException("NewBarrier: Expression is not int!");
            }
            int nr = ((IntValue) number).getValue();

            int freeAddr = barrierTable.getFreeAddress();

            barrierTable.put(freeAddr, new Pair<>(nr, new ArrayList<>()));

            if (symTable.isDefined(var) && symTable.get(var).getType().equals(new IntType())) {
                symTable.update(var, new IntValue(freeAddr));
            } else {
                throw new MyException("NewBarrier: Variable not defined or not int!");
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.get(var);
        Type typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(new IntType()) && typeExp.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("NewBarrier: Both var and exp must be int!");
    }

    @Override
    public IStmt deepCopy() {
        return new NewBarrierStmt(var, exp.deepCopy());
    }

    @Override
    public String toString() {
        return "newBarrier(" + var + ", " + exp + ")";
    }
}