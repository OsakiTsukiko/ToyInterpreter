package osaki.mylang.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import osaki.mylang.controller.Controller;
import osaki.mylang.model.PrgState;
import osaki.mylang.model.adts.MyDictionary;
import osaki.mylang.model.adts.MyHeap;
import osaki.mylang.model.adts.MyList;
import osaki.mylang.model.adts.MyStack;
import osaki.mylang.model.stmt.IStmt;
import osaki.mylang.model.values.IValue;
import osaki.mylang.model.values.StringValue;
import osaki.mylang.repository.Repo;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static osaki.mylang.controller.GarbageCollector.conservativeGarbageCollector;

public class ProgramRunner {
    public static void show(Stage stage, IStmt program) {
        Controller cont = new Controller(
                new Repo(
                        new PrgState(
                                new MyStack<>(),
                                new MyDictionary<>(),
                                new MyList<>(),
                                new MyDictionary<>(),
                                new MyHeap<>(),
                                program
                        ),
                        "ui_log.txt"
                )
        );

        VBox root = new VBox();

        TextField nrOfPrograms = new TextField("Nr Of Programs: NaN");
        TextField selectedPrg = new TextField("Selected Prg: NaN");
        // nrOfPrograms.setMaxWidth(Double.MAX_VALUE);

        GridPane container = new GridPane();
        container.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(container, Priority.ALWAYS );
        container.setPadding(new Insets(10));
        container.setHgap(4);
        container.setVgap(8);

        TableView<Pair<Integer, IValue>> heapTable = new TableView<>();
        TableView<Pair<String, IValue>> symTable = new TableView<>();

        ListView<String> output = new ListView<>();
        ListView<String> filetable = new ListView<>();
        ListView<Integer> prgStateList = new ListView<>();
        ListView<String> exeStack = new ListView<>();
        exeStack.minWidthProperty().set(700f);

        container.add(new VBox(new Text("Heap Table"), heapTable), 0, 0);
        container.add(new VBox(new Text("Output"), output), 1, 0);
        container.add(new VBox(new Text("File Table"), filetable), 2, 0);
        container.add(new VBox(new Text("Program List"), prgStateList), 0, 1);
        container.add(new VBox(new Text("Sym Table"), symTable), 1, 1);
        container.add(new VBox(new Text("Exe Stack"), exeStack), 2, 1);

        Runnable update = () -> {
            nrOfPrograms.textProperty().set("Nr Of Programs: " + Integer.toString(cont.getPrgCount()));

            {
                // Update heap.
                TableColumn<Pair<Integer, IValue>, Integer> addressColumn = new TableColumn<Pair<Integer, IValue>, Integer>("Address");
                addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
                addressColumn.setPrefWidth(100);

                TableColumn<Pair<Integer, IValue>, String> valueColumn = new TableColumn<Pair<Integer, IValue>, String>("Value");
                valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
                valueColumn.setPrefWidth(320);
                valueColumn.setSortable(false);

                heapTable.getColumns().clear();
                heapTable.getColumns().addAll(addressColumn, valueColumn);

                heapTable.editableProperty().set(false);
                heapTable.getItems().clear();

                for (var entry : cont.getHeap().entrySet()) {
                    // System.out.println(entry.getKey() + "/" + entry.getValue());
                    heapTable.getItems().add(new Pair<>(entry.getKey(), entry.getValue()));
                }
                heapTable.refresh();
            }

            {
                // Update Output

                List<String> values = new ArrayList<>();
                Iterator<IValue> iterator = cont.getOut();
                while (iterator.hasNext()) {
                    IValue value = iterator.next();
                    values.add(value.toString());
                }

                ObservableList<String> observableList = FXCollections.observableArrayList(values);
                output.setItems(observableList);

                // Filelist Output

                List<String> values_fl = new ArrayList<>();
                Iterator<StringValue> iterator_fl = cont.getFileTable();
                while (iterator_fl.hasNext()) {
                    IValue value = iterator_fl.next();
                    values_fl.add(value.toString());
                }

                ObservableList<String> observableList_fl = FXCollections.observableArrayList(values_fl);
                filetable.setItems(observableList_fl);
            }


            // PrgList Output

            List<Integer> values_pl = new ArrayList<>();
            Iterator<PrgState> iterator_pl = cont.getProgStates();
            while (iterator_pl.hasNext()) {
                PrgState value = iterator_pl.next();
                values_pl.add(value.id);
            }

            ObservableList<Integer> observableList_pl = FXCollections.observableArrayList(values_pl);
            prgStateList.setItems(observableList_pl);

            Integer selected_prg = cont.repo.getPrgList().get(0).id;
            Integer selectedItem = prgStateList.getSelectionModel().getSelectedItem();
            if (selectedItem != null) selected_prg = selectedItem;

//            boolean has_prg = false;
//            for (PrgState prg : cont.repo.getPrgList()) {
//                if (selected_prg[0].equals(prg.id)) has_prg = true;
//                break;
//            }
//            if (!has_prg) selected_prg[0] = cont.repo.getPrgList().get(0).id;

            selectedPrg.textProperty().set("Selected Prg: " + selected_prg.toString());

            {
                // Update heap.
                TableColumn<Pair<String, IValue>, String> addressColumn = new TableColumn<>("Name");
                addressColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
                addressColumn.setPrefWidth(100);

                TableColumn<Pair<String, IValue>, String> valueColumn = new TableColumn<>("Value");
                valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
                valueColumn.setPrefWidth(320);
                valueColumn.setSortable(false);

                symTable.getColumns().clear();
                symTable.getColumns().addAll(addressColumn, valueColumn);

                symTable.editableProperty().set(false);
                symTable.getItems().clear();

                for (var entry : cont.getSymTable(selected_prg).entrySet()) {
                    // System.out.println(entry.getKey() + "/" + entry.getValue());
                    symTable.getItems().add(new Pair<>(entry.getKey(), entry.getValue()));
                }
                symTable.refresh();
            }

            {
                // Update Exe Stack

                List<String> values = new ArrayList<>();
                Iterator<IStmt> iterator = cont.getExeStack(selected_prg);
                while (iterator.hasNext()) {
                    IStmt value = iterator.next();
                    values.add(value.toString());
                }
                Collections.reverse(values);

                ObservableList<String> observableList = FXCollections.observableArrayList(values);
                exeStack.setItems(observableList);
            }
        };

        prgStateList.setOnMouseClicked(event -> {
            update.run();
        });


        Button run = new Button("Run Program!");
        run.setMaxWidth(Double.MAX_VALUE);
        run.setOnAction(event -> {
            List<PrgState> prgList=cont.removeCompletedPrg(cont.repo.getPrgList());
            if (prgList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("OUUPS!");
                alert.setHeaderText("Empty Exe Stack");
                alert.setContentText("The prgram already finished!!");

                alert.showAndWait();
                return;
            }
            cont.executor = Executors.newFixedThreadPool(2);
            conservativeGarbageCollector(prgList);
            try {
                cont.oneStepForAllPrg(prgList);
            } catch (Exception err) {
                // System.out.println("UPSSS: " + err.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("OUUPS!");
                alert.setHeaderText("Runtime ERROR");
                alert.setContentText(err.getMessage());

                alert.showAndWait();
            }
            cont.executor.shutdownNow();

            update.run();
        });

        root.getChildren().addAll(nrOfPrograms, selectedPrg, container, run);

        Scene scene = new Scene(root, 400, 720);

        stage.setTitle("MyLang - Run Program");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        update.run();
    }
}
