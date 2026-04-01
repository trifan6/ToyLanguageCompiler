package GUI;

import Controller.Controller;
import Model.ADT.*;
import Model.PrgState;
import Model.Statements.*;
import Model.Expressions.*;
import Model.Values.*;
import Model.Types.*;
import Repository.IRepository;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ProgramChooserController
{

    @FXML
    private ListView<IStmt> programsListView;
    @FXML
    private Button displayButton;

    @FXML
    public void initialize()
    {
        programsListView.setItems(getAllExamples());
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void displayProgram(ActionEvent actionEvent)
    {
        IStmt selectedStmt = programsListView.getSelectionModel().getSelectedItem();
        if (selectedStmt == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No program selected!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        int id = programsListView.getSelectionModel().getSelectedIndex() + 1;
        try {
            selectedStmt.typecheck(new MyDictionary<>());
            PrgState prg = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyTable(), new MyHeap(), selectedStmt, new MyBarrierTable());
            IRepository repo = new Repository(prg, "log" + id + ".txt");
            Controller controller = new Controller(repo);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProgramExecutor.fxml"));
            Parent root = loader.load();

            ProgramExecutorController executorController = loader.getController();
            executorController.setController(controller);

            Stage stage = new Stage();
            stage.setTitle("Program Executor");
            stage.setScene(new Scene(root, 900, 600));
            stage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private ObservableList<IStmt> getAllExamples()
    {
        List<IStmt> allStmts = new ArrayList<>();

        IStmt ex1 = new CompStmt(
                new VarDeclStmt("v", new BoolType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );
        allStmts.add(ex1);

        IStmt ex2 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt("a",
                                        new ArithExp("+",
                                                new ValueExp(new IntValue(2)),
                                                new ArithExp("*",
                                                        new ValueExp(new IntValue(3)),
                                                        new ValueExp(new IntValue(5)))
                                        )
                                ),
                                new CompStmt(new AssignStmt("b",
                                        new ArithExp("+",
                                                new VarExp("a"),
                                                new ValueExp(new IntValue(1)))
                                ),
                                        new PrintStmt(new VarExp("b"))
                                )
                        )
                )
        );
        allStmts.add(ex2);

        IStmt ex3 = new CompStmt(
                new VarDeclStmt("a", new BoolType()),
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(
                                                new VarExp("a"),
                                                new AssignStmt("v", new ValueExp(new IntValue(2))),
                                                new AssignStmt("v", new ValueExp(new IntValue(3)))
                                        ),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                )
        );
        allStmts.add(ex3);

        IStmt ex4 = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(
                                new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(
                                                                new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        allStmts.add(ex4);

        IStmt ex5 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ValueExp(new IntValue(2))),
                                new IfStmt(
                                        new RelExp(
                                                new VarExp("a"),
                                                new ValueExp(new IntValue(2)),
                                                "=="),
                                        new PrintStmt(new ValueExp(new StringValue("true"))),
                                        new PrintStmt(new ValueExp(new StringValue("false")))
                                )
                        )
                )
        );
        allStmts.add(ex5);

        IStmt ex6 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(
                                        new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(
                                                new ArithExp("+",
                                                        new ReadHeapExp(new VarExp("v")),
                                                        new ValueExp(new IntValue(5))
                                                )
                                        )
                                )
                        )
                )
        );
        allStmts.add(ex6);

        IStmt ex7 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(
                                                        new ArithExp("+",
                                                                new ReadHeapExp(
                                                                        new ReadHeapExp(
                                                                                new VarExp("a")
                                                                        )
                                                                ),
                                                                new ValueExp(new IntValue(0))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        allStmts.add(ex7);

        IStmt ex8 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new AssignStmt("v",
                                                        new ArithExp("-",
                                                                new VarExp("v"),
                                                                new ValueExp(new IntValue(1))))
                                        )
                                ),
                                new PrintStmt(new VarExp("v"))
                        )
                )
        );
        allStmts.add(ex8);

        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new VarDeclStmt("x", new RefType(new IntType())),
                                                                        new CompStmt(
                                                                                new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                                new CompStmt(
                                                                                        new PrintStmt(new VarExp("v")),
                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                                                )
                                                                        )

                                                                )
                                                        )),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));

        allStmts.add(ex9);

        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new AssignStmt("v", new ValueExp(new BoolValue(true))));
        allStmts.add(ex10);

        IStmt exExam2 = new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(
                                new VarDeclStmt("v3", new RefType(new IntType())),
                                new CompStmt(
                                        new VarDeclStmt("cnt", new IntType()),
                                        new CompStmt(
                                                new NewStmt("v1", new ValueExp(new IntValue(2))),
                                                new CompStmt(
                                                        new NewStmt("v2", new ValueExp(new IntValue(3))),
                                                        new CompStmt(
                                                                new NewStmt("v3", new ValueExp(new IntValue(4))),
                                                                new CompStmt(
                                                                        new NewBarrierStmt("cnt", new ReadHeapExp(new VarExp("v2"))),
                                                                        new CompStmt(
                                                                                new ForkStmt(
                                                                                        new CompStmt(
                                                                                                new AwaitStmt("cnt"),
                                                                                                new CompStmt(
                                                                                                        new WriteHeapStmt("v1", new ArithExp("*", new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v1")))
                                                                                                )
                                                                                        )
                                                                                ),
                                                                                new CompStmt(
                                                                                        new ForkStmt(
                                                                                                new CompStmt(
                                                                                                        new AwaitStmt("cnt"),
                                                                                                        new CompStmt(
                                                                                                                new WriteHeapStmt("v2", new ArithExp("*", new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                                new CompStmt(
                                                                                                                        new WriteHeapStmt("v2", new ArithExp("*", new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v2")))
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompStmt(
                                                                                                new AwaitStmt("cnt"),
                                                                                                new PrintStmt(new ReadHeapExp(new VarExp("v3")))
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        allStmts.add(exExam2);

        return FXCollections.observableArrayList(allStmts);
    }
}