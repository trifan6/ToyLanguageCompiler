package Model.ADT;

import Exceptions.MyException;
import javafx.util.Pair;
import java.util.HashMap;
import java.util.List;

public interface MyIBarrierTable {
    void put(int key, Pair<Integer, List<Integer>> value);
    Pair<Integer, List<Integer>> get(int key) throws MyException;
    boolean containsKey(int key);
    int getFreeAddress();
    void update(int key, Pair<Integer, List<Integer>> value);
    HashMap<Integer, Pair<Integer, List<Integer>>> getContent();
    void setContent(HashMap<Integer, Pair<Integer, List<Integer>>> newMap);
}