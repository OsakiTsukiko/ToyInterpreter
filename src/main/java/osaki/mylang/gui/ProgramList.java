package osaki.mylang.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import osaki.mylang.model.stmt.IStmt;

import java.util.ArrayList;

public class ProgramList {
    public static void show(Stage stage, ArrayList<IStmt> program_list) {
        VBox root = new VBox();

        ListView<String> display_list = new ListView<>();

        // display_list.getItems().add("HELLO!");

        for (IStmt program : program_list) {
            display_list.getItems().add(program.toString());
        }

        Button run = new Button("Run Program!");
        run.setMaxWidth(Double.MAX_VALUE);
        run.setOnAction(event -> {
            int selectedItem = display_list.getSelectionModel().getSelectedIndex();
            if (selectedItem == -1) return;
            // System.out.println(program_list.get(selectedItem).toString());
            ProgramRunner.show(stage, program_list.get(selectedItem));
        });

        root.getChildren().addAll(display_list, run);
        VBox.setVgrow(display_list, Priority.ALWAYS);

        Scene scene = new Scene(root, 400, 720);

        stage.setTitle("MyLang - Select Program");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
