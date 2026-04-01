package GUI;

import java.util.List;

public class BarrierTableEntry {
    private int address;
    private int value;
    private List<Integer> list;

    public BarrierTableEntry(int address, int value, List<Integer> list) {
        this.address = address;
        this.value = value;
        this.list = list;
    }
    public int getAddress() { return address; }
    public int getValue() { return value; }
    public List<Integer> getList() { return list; }
}