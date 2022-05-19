package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.controller.admin.AdminController;

import java.net.URL;

public class View extends Application {

   /* @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ABS");
        Parent load = FXMLLoader.load(getClass().getResource("controller/app/application.fxml"));
        Scene scene = new Scene(load, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

    }*/
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("controller/app/application.fxml");
        fxmlLoader.setLocation(url);
        Parent root = fxmlLoader.load(url.openStream());
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}


