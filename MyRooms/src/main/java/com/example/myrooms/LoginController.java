package com.example.myrooms;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.*;

public class LoginController implements Initializable {

    @FXML
    StackPane stackPane;
    @FXML
    AnchorPane anchorPane;
    @FXML
    ImageView imageView;
    @FXML
    Pane RotatingDoorPane;
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
    ToggleButton loginvisitButton;
    @FXML
    Pane registerPane;
    @FXML
    TextField registerUsername;
    @FXML
    TextField registerPassword;
    @FXML
    TextField registerConfirmPassword;
    @FXML
    Button registerButton;
    @FXML
    Button registration;
    @FXML
    ImageView LockImage;
    @FXML
    TextField visitUsername;

    private static final String DataBase_FILE = "MyRooms/src/main/resources/Users";
    public static final LinkedHashMap<String,String> database = new LinkedHashMap<>();
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    private int mode = 0; // 0 Login -- 1 Register -- 2 Visit
    static public String name;
    User user;
    static int UserID = 1;

    public boolean isAdmin = true;


    public void goToRoom() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainRoom.fxml"));
        Parent root = loader.load();

        MainController mainController = loader.getController();

        mainController.setName(name);
        mainController.stackPane = stackPane;
        loadRoom(name,mainController);
        mainController.isAdmin = isAdmin;

        RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setByAngle(70);
        rotate.setDuration(Duration.seconds(2));
        rotate.setNode(RotatingDoorPane);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1.5), anchorPane);
        scaleTransition.setToX(20.0);
        scaleTransition.setToY(20.0);
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
    public void fadeIn(){
        FadeTransition fadein = new FadeTransition(Duration.seconds(0.8), LockImage);
        fadein.setFromValue(0.0);
        fadein.setToValue(0.9);
        fadein.setCycleCount(2);
        fadein.setAutoReverse(true);
        fadein.play();
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), LockImage);
        translateTransition.setFromX(0);
        translateTransition.setToX(10);
        translateTransition.setCycleCount(10);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }
    public void changePane(){
        VisitPane.setVisible(!VisitPane.isVisible());
        loginPane.setVisible(!loginPane.isVisible());
        if(loginPane.isVisible()){
            loginvisitButton.setText("Login");
            mode = 0;
        }
        else{
            loginvisitButton.setText("Visit");
            mode = 2;
        }

    }
    public static void saveDatabase() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DataBase_FILE))) {
            for (Map.Entry<String, String> entry : database.entrySet()) {
                writer.println(entry.getKey() + " : " + entry.getValue());

            }
        } catch (IOException e) {
            System.err.println("Veritabanı kaydedilirken bir hata oluştu: " + e.getMessage());
        }
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
            System.err.println("Error in loading");
        }
    }
    public void showRegister(){
        registerPane.setVisible(!registerPane.isVisible());
        if(registerPane.isVisible()){
            mode = 1;
        }
        else
            mode = 0;
    }
    public void registerNew() throws IOException {

        if(mode == 1 && !registerPassword.getText().isEmpty() && !registerConfirmPassword.getText().isEmpty() && !registerUsername.getText().isEmpty()){

            if(registerPassword.getText().equals(registerConfirmPassword.getText())){
                if(!database.containsKey(registerUsername.getText())){
                    database.put(registerUsername.getText(),registerPassword.getText());
                    name = registerUsername.getText();
                    System.out.println(name);
                    saveDatabase();

                    Clock clock1 = new Clock();
                    Watercan watercan = new Watercan(0);
                    Room userRoom = new Room(new Alarm(clock1),clock1,new Clock(),new BookCase(),new Board(),new Plant(0,watercan),new Notebooks(),new Calendars(),new Shop(),new Settings(),new MusicPlayer(), new Charts());

                    user = new User(registerUsername.getText(),registerPassword.getText(),100,0,userRoom,MyRoom.UserID);
                    UserManager.USER_FILE = name + ".ser";
                    UserManager.saveUser(user);
                    MyRoom.UserID++;

                    goToRoom();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Username");
                    alert.setHeaderText(null);
                    alert.setContentText("This username have been selected! Please select different username");
                    alert.show();
                }

            }
        }
    }
    public void isValid() throws IOException {

        if (mode == 0) {
            if(!username.getText().isEmpty() && !password.getText().isEmpty()) {

               if(database.get(username.getText()) != null && database.get(username.getText()).equals(password.getText())) {

                   try {
                       name = username.getText();

                       isAdmin = true;
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
                   fadeIn();
               }
            }
            else{
                ErrorText.setText("Username or Password is Empty");
                username.clear();
                password.clear();
                fadeIn();
            }
        }
        else if(mode == 2){

            if(!visitUsername.getText().isEmpty()){

                if(database.containsKey(visitUsername.getText())){
                    isAdmin = false;
                    name = visitUsername.getText();
                    goToRoom();

                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDatabase();
        ErrorText.setStyle(IDLE_BUTTON_STYLE);

    }
    public void loadRoom(String user, MainController controller) throws IOException {

        controller.name = name;
    }
}
