package Model.ADT;

import Exceptions.MyException;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.util.Map;

public interface MyITable
{
    void put(String key, BufferedReader value) throws MyException;

    boolean isDefined(StringValue key);

    void remove(String key) throws MyException;

    BufferedReader get(String key) throws MyException;

    Map<String, BufferedReader> getAll();
}
