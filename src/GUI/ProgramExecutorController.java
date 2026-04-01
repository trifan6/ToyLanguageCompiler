package GUI;

import Controller.Controller;
import Model.ADT.MyIBarrierTable;
import Model.PrgState;
import Model.Statements.IStmt;
import Model.Values.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgramExecutorController {
    private Controller controller;

    @FXML private TextField numberOfPrgStatesTextField;
    @FXML private TableView<HeapEntry> heapTableView;
    @FXML private TableColumn<HeapEntry, Integer> addressColumn;
    @FXML private TableColumn<HeapEntry, String> valueColumn;
    @FXML private ListView<String> outputListView;
    @FXML private ListView<String> fileTableListView;
    @FXML private ListView<Integer> prgStateIdentifiersListView;
    @FXML private TableView<SymTableEntry> symbolTableView;
    @FXML private TableColumn<SymTableEntry, String> variableNameColumn;
    @FXML private TableColumn<SymTableEntry, String> variableValueColumn;
    @FXML private ListView<String> executionStackListView;
    @FXML private Button runOneStepButton;
    @FXML private TableView<BarrierTableEntry> barrierTableView;
    @FXML private TableColumn<BarrierTableEntry, Integer> barrierAddressColumn;
    @FXML private TableColumn<BarrierTableEntry, Integer> barrierValueColumn;
    @FXML private TableColumn<BarrierTableEntry, List<Integer>> barrierListColumn;

    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    @FXML
    public void initialize() {
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        variableNameColumn.setCellValueFactory(new PropertyValueFactory<>("variableName"));
        variableValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        barrierAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        barrierValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        barrierListColumn.setCellValueFactory(new PropertyValueFactory<>("list"));
    }

    @FXML
    public void runOneStep(ActionEvent event) {
        if (controller == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No program selected!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            List<PrgState> prgList = controller.removeCompletedPrg(controller.getRepo().getPrgList());
            if (prgList.size() > 0) {
                controller.oneStepForAll(prgList);
                populate();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Program finished!", ButtonType.OK);
                alert.showAndWait();
                controller.getRepo().setPrgList(prgList);
                populate();
            }
        } catch (InterruptedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void changeProgramState(MouseEvent event) {
        populateSymbolTable();
        populateExecutionStack();
    }

    private void populate() {
        populateHeap();
        populateProgramStateIdentifiers();
        populateFileTable();
        populateOutput();
        populateSymbolTable();
        populateExecutionStack();
        populateBarrierTable();
        numberOfPrgStatesTextField.setText(String.valueOf(controller.getRepo().getPrgList().size()));
    }

    private void populateBarrierTable() {
        if (controller.getRepo().getPrgList().size() > 0) {
            MyIBarrierTable barrierTable = controller.getRepo().getPrgList().get(0).getBarrierTable();
            List<BarrierTableEntry> list = new ArrayList<>();
            for (var entry : barrierTable.getContent().entrySet()) {
                list.add(new BarrierTableEntry(entry.getKey(), entry.getValue().getKey(), entry.getValue().getValue()));
            }
            barrierTableView.setItems(FXCollections.observableArrayList(list));
            barrierTableView.refresh();
        }
    }

    private void populateHeap() {
        PrgState prgState = getCurrentProgramState();
        if (prgState == null || prgState.getHeap() == null) return;

        Map<Integer, Value> heap = prgState.getHeap().getContent();
        List<HeapEntry> heapEntries = new ArrayList<>();
        for (Map.Entry<Integer, Value> entry : heap.entrySet()) {
            heapEntries.add(new HeapEntry(entry.getKey(), entry.getValue()));
        }
        heapTableView.setItems(FXCollections.observableArrayList(heapEntries));
    }

    private void populateSymbolTable() {
        PrgState prgState = getCurrentProgramState();
        List<SymTableEntry> symTableEntries = new ArrayList<>();
        if (prgState != null) {
            for (Map.Entry<String, Value> entry : prgState.getSymTable().getAll().entrySet()) {
                symTableEntries.add(new SymTableEntry(entry.getKey(), entry.getValue()));
            }
        }
        symbolTableView.setItems(FXCollections.observableArrayList(symTableEntries));
    }

    private void populateProgramStateIdentifiers() {
        List<PrgState> prgStates = controller.getRepo().getPrgList();
        List<Integer> ids = prgStates.stream().map(PrgState::getId).collect(Collectors.toList());
        prgStateIdentifiersListView.setItems(FXCollections.observableArrayList(ids));

        if (ids.size() > 0 && prgStateIdentifiersListView.getSelectionModel().getSelectedItem() == null) {
            prgStateIdentifiersListView.getSelectionModel().select(0);
        }
    }

    private void populateExecutionStack() {
        PrgState prgState = getCurrentProgramState();
        List<String> stackList = new ArrayList<>();
        if (prgState != null) {
            for (IStmt stmt : prgState.getExeStack().getReversed()) {
                stackList.add(stmt.toString());
            }
        }
        executionStackListView.setItems(FXCollections.observableArrayList(stackList));
    }

    private void populateFileTable() {
        PrgState prgState = getCurrentProgramState();
        List<String> files = new ArrayList<>();
        if (prgState != null) {
            files.addAll(prgState.getFileTable().getAll().keySet());
        }
        fileTableListView.setItems(FXCollections.observableArrayList(files));
    }

    private void populateOutput() {
        PrgState prgState = getCurrentProgramState();
        List<String> output = new ArrayList<>();
        if (prgState != null) {
            for (Value v : prgState.getOut().getAll()) {
                output.add(v.toString());
            }
        }
        outputListView.setItems(FXCollections.observableArrayList(output));
    }

    private PrgState getCurrentProgramState() {
        if (controller.getRepo().getPrgList().size() == 0) return null;
        int currentId = prgStateIdentifiersListView.getSelectionModel().getSelectedItem() != null
                ? prgStateIdentifiersListView.getSelectionModel().getSelectedItem()
                : 0;
        return controller.getRepo().getPrgList().stream()
                .filter(p -> p.getId() == currentId).findFirst().orElse(null);
    }
}