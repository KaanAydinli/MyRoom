package com.example.myrooms;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    StackPane stackPane;
    @FXML
    AnchorPane anchorPane;
    @FXML
    ImageView imageView;
    @FXML
    Pane myPane;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    TextField ErrorText;
    @FXML
    Pane loginPane;
    @FXML
    Pane VisitPane;
    @FXML
    ToggleButton button;

    private static final String DataBase_FILE = "src/main/resources/databaseRoom";
    private static final Map<String,String> database = new HashMap<>();
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    static public String name;

    public void goToRoom() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("mainRoom.fxml"));


        RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setByAngle(70);
        rotate.setDuration(Duration.seconds(2));
        rotate.setNode(myPane);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1.5), anchorPane);
        scaleTransition.setToX(8.0);
        scaleTransition.setToY(8.0);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setOnFinished(event ->{
            root.scaleXProperty().set(8.0);
            root.scaleYProperty().set(8.0);
            stackPane.getChildren().add(root);

            stackPane.getChildren().remove(anchorPane);

            ScaleTransition scaleTransition2 = new ScaleTransition(Duration.seconds(1.5), root);
            scaleTransition2.setToX(1.0);
            scaleTransition2.setToY(1.0);
            scaleTransition2.setFromX(8);
            scaleTransition2.setFromY(8);

            scaleTransition2.play();

        });
        rotate.play();
        rotate.setOnFinished(event -> {
            scaleTransition.play();
        });

    }
    public void changePane(){
        VisitPane.setVisible(!VisitPane.isVisible());
        loginPane.setVisible(!loginPane.isVisible());
        if(loginPane.isVisible()){
            button.setText("Login");
        }
        else
            button.setText("Visit");
    }
    private  void loadDatabase(){
        try(BufferedReader reader = new BufferedReader(new FileReader(DataBase_FILE))) {
            String line;

            while((line = reader.readLine()) != null){

                String[] parts = line.split(":");
                String question = parts[0].trim();
                String response = parts[1].trim();
                database.put(question, response);

            }
        } catch (IOException e) {
            System.err.println("error");
        }
    }
    public void isValid() {

        if(!username.getText().isEmpty() && !password.getText().isEmpty()) {

           if(database.get(username.getText()) != null && database.get(username.getText()).equals(password.getText())) {

               try {
                   name = username.getText();
                   goToRoom();
               } catch (Exception e) {
                   e.printStackTrace();
               }
               ErrorText.setText("");
           }
           else{
               username.clear();
               password.clear();
               ErrorText.setText("Wrong Password");
           }
        }
        else{
            ErrorText.setText("Username or Password is Empty");
            username.clear();
            password.clear();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDatabase();
        ErrorText.setStyle(IDLE_BUTTON_STYLE);

    }
}
