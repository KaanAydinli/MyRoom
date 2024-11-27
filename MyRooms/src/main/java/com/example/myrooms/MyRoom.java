package com.example.myrooms;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.*;


public class MyRoom extends Application {


    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginRoom.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        //Parent root = FXMLLoader.load(getClass().getResource("mainRoom.fxml"));

        Scene scene = new Scene(root);


        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setTitle("My Room");
        stage.setScene(scene);
        stage.setResizable(false);

        Platform.setImplicitExit(false);
        stage.setOnCloseRequest(event -> {
            controller.saveDatabase();
        });

        Image image = new Image("CsProject-BackGrounds/planticon.png");
        stage.getIcons().add(image);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}