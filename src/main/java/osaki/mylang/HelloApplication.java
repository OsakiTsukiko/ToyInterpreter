package osaki.mylang;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // A node of type Group is created
        /* Group group = new Group();
        // A node of type Rectangle is created
        Rectangle r = new Rectangle(25,25,50,50);
        r.setFill(Color.BLUE);
        group.getChildren().add(r);
        // A node of type Circle is created
        Circle c = new Circle(200,200,50, Color.web("blue", 0.5f));
        group.getChildren().add(c); */

        HBox root = new HBox();
        root.setAlignment(Pos.BASELINE_CENTER);
        root.setFillHeight(true);

        Button b1 = new Button("b1");
        Button b2 = new Button("b2");
        Button b3 = new Button("b3");
        b1.setPrefHeight(Double.MAX_VALUE);

        HBox.setHgrow(b1, Priority.ALWAYS);
        HBox.setHgrow(b2, Priority.ALWAYS);
        HBox.setHgrow(b3, Priority.ALWAYS);

        b1.setMaxWidth(Double.MAX_VALUE);
        b2.setMaxWidth(Double.MAX_VALUE);
        b3.setMaxWidth(Double.MAX_VALUE);

        root.getChildren().addAll(b1, b2, b3);

        Scene scene = new Scene(root, 1080, 720);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}