package Model.Statements;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyITable;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt
{
    private final Exp exp;
    private final String varName;

    public ReadFileStmt(Exp exp, String varName)
    {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if(!symTable.isDefined(varName))
        {
            throw new MyException("ReadFile: variable "+varName+" is not defined");
        }
        Value varVal = symTable.get(varName);
        if (!(varVal.getType().equals(new IntType())))
            throw new MyException("readFile: variable " + varName + " is not of type int.");

        Value v = exp.eval(symTable, state.getHeap());
        if (!(v instanceof StringValue))
            throw new MyException("readFile: filename expression does not evaluate to a string.");
        StringValue sVal = (StringValue) v;

        MyITable ft = state.getFileTable();
        if (!ft.isDefined(sVal))
            throw new MyException("readFile: file " + sVal.getVal() + " is not opened.");

        BufferedReader br = ft.get(sVal.toString());
        try
        {
            String line = br.readLine();
            int readInt;
            if (line == null)
            {
                readInt = 0;
            }
            else
            {
                try
                {
                    readInt = Integer.parseInt(line.trim());
                }
                catch (NumberFormatException nfe)
                {
                    throw new MyException("readFile: cannot parse integer from line: \"" + line + "\"");
                }
            }
            symTable.update(varName, new IntValue(readInt));
        }
        catch (IOException e)
        {
            throw new MyException("readFile: IO error while reading file " + sVal.getVal() + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeexp = exp.typecheck(typeEnv);
        Type typevar = typeEnv.get(varName);

        if (!typeexp.equals(new StringType()))
            throw new MyException("ReadFile requires a string expression for filepath");
        if (!typevar.equals(new IntType()))
            throw new MyException("ReadFile requires an int variable to store result");

        return typeEnv;
    }

    @Override
    public String toString()
    {
        return "readFile("+exp.toString()+","+varName+")";
    }

    @Override
    public IStmt deepCopy()
    {
        return new ReadFileStmt(exp.deepCopy(), varName);
    }
}
