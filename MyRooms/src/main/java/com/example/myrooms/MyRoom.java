package com.example.myrooms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class MyRoom extends Application {


    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("loginRoom.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("mainRoom.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("My Room");
        stage.setScene(scene);
        stage.setResizable(false);


        Image image = new Image("CsProject-BackGrounds/planticon.png");
        stage.getIcons().add(image);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}