package GUI;

import javafx.beans.property.SimpleStringProperty;
import Model.Values.Value;

public class SymTableEntry {
    private final SimpleStringProperty variableName;
    private final SimpleStringProperty value;

    public SymTableEntry(String variableName, Value value) {
        this.variableName = new SimpleStringProperty(variableName);
        this.value = new SimpleStringProperty(value.toString());
    }

    public String getVariableName() { return variableName.get(); }
    public void setVariableName(String variableName) { this.variableName.set(variableName); }
    public String getValue() { return value.get(); }
    public void setValue(Value value) { this.value.set(value.toString()); }
}