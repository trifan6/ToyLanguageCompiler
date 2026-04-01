package GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import Model.Values.Value;

public class HeapEntry {
    private final SimpleIntegerProperty address;
    private final SimpleStringProperty value;

    public HeapEntry(Integer address, Value value) {
        this.address = new SimpleIntegerProperty(address);
        this.value = new SimpleStringProperty(value.toString());
    }

    public Integer getAddress() { return address.get(); }
    public void setAddress(Integer address) { this.address.set(address); }
    public String getValue() { return value.get(); }
    public void setValue(Value value) { this.value.set(value.toString()); }
}