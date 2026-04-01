package Model.Statements;

import Model.ADT.MyIDictionary;
import Exceptions.MyException;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class VarDeclStmt implements IStmt
{
    private final String name;
    private final Type type;

    public VarDeclStmt(String name, Type type)
    {
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if(symTable.isDefined(name))
        {
            throw new MyException("Variable already exists: " + name);
        }

        symTable.put(name, type.defaultValue());
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException
    {
        typeEnv.put(name, type);
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return type.toString() + " " + name;
    }

    @Override
    public IStmt deepCopy()
    {
        return new VarDeclStmt(name, type.deepCopy());
    }

}
