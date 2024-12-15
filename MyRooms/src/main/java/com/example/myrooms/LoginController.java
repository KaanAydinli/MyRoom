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

    private static final String DataBase_FILE = "src/main/resources/Users";
    private static final LinkedHashMap<String,String> database = new LinkedHashMap<>();
    private static  LinkedHashMap<String,String> roomDatabase = new LinkedHashMap<>();
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    public String name;

    public void goToRoom() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainRoom.fxml"));
        Parent root = loader.load();

        MainController mainController = loader.getController();
        mainController.setName(name);
        mainController.UserDatabase = roomDatabase;
        loadRoom(name,mainController);

        //Specifically initializes notebook object
        mainController.initializeNotebookCalendar();


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
        }
        else
            loginvisitButton.setText("Visit");
    }

    public void saveDatabase() {
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
    }
    public void registerNew() throws IOException {

        if(!registerPassword.getText().isEmpty() && !registerConfirmPassword.getText().isEmpty() && !registerUsername.getText().isEmpty()){

            if(registerPassword.getText().equals(registerConfirmPassword.getText())){
                database.put(registerUsername.getText(),registerPassword.getText());
                name = registerUsername.getText();
                System.out.println(name);
                saveDatabase();
                try {
                    FileWriter writer = new FileWriter("src/main/resources/UserDatabases/" +  name + "Database");
                    writer.write("TotalCoin : 50\n");
                    writer.write("Plant : CsProject-BackGrounds/Plant.png\n");
                    writer.write("Bookcase : CsProject-BackGrounds/Bookcase.png\n");
                    writer.write("Alarm : CsProject-BackGrounds/Alarm.png\n");
                    writer.write("Calendar : CsProject-BackGrounds/Calendar.png\n");
                    writer.write("Clock : CsProject-BackGrounds/Clock.png");

                    writer.close();
                } catch (IOException e) {
                    System.err.println("An error occurred: " + e.getMessage());
                }
                goToRoom();
            }
        }
    }
    public void isValid() {
       //for development
        name = username.getText();

        try{
            goToRoom();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

//        if(!username.getText().isEmpty() && !password.getText().isEmpty()) {
//
//           if(database.get(username.getText()) != null && database.get(username.getText()).equals(password.getText())) {
//
//               try {
//                   name = username.getText();
//
//                   goToRoom();
//               } catch (Exception e) {
//                   e.printStackTrace();
//               }
//               ErrorText.setText("");
//           }
//           else{
//               username.clear();
//               password.clear();
//               ErrorText.setText("Wrong Password");
//               fadeIn();
//           }
//        }
//        else{
//            ErrorText.setText("Username or Password is Empty");
//            username.clear();
//            password.clear();
//        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDatabase();
        ErrorText.setStyle(IDLE_BUTTON_STYLE);

    }
    public void loadRoom(String user, MainController controller) throws IOException {


        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/UserDatabases/" + user + "Database"))) {
            String line;

            while((line = reader.readLine()) != null){

                if (line.trim().isEmpty() || line.startsWith("#")) {
                    roomDatabase.put(line, "");
                }
                else {
                    String[] parts = line.split(":");
                    String variable = parts[0].trim();
                    String value = parts[1].trim();

                    roomDatabase.put(variable, value);
                    if (variable.equals("TotalCoin")) {
                        controller.setCoin(Integer.parseInt(value));
                    }
                    if (variable.equals("Clock")) {
                        controller.buyClock(value);
                    }
                    if (variable.equals("Alarm")) {
                        controller.setAlarmImage(value);
                    }
                    if (variable.equals("Bookcase")) {
                        controller.setBookcaseImage(value);
                    }
                    if (variable.equals("Calendar")) {
                        controller.setCalendarImage(value);
                    }
                    if (variable.equals("Board")) {
                        controller.setBoardImage(value);
                    }
                    if (variable.equals("Plant")) {
                        controller.setPlantImage(value);
                    }
                    if (variable.equals("TotalTimeSpent")) {
                        controller.setTotalTime(value);
                    }
                    if (variable.equals("Username")) {
                        controller.setName(value);
                    }
                    if (variable.equals("Postits")) {
                        for (int i = 0; i < Integer.parseInt(value); i++) {
                            controller.corkBoardScene();
                        }
                    }
                    if(variable.equals("ShopIcon")){
                        controller.setShopIcon(value);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error on loading");
        }
    }

}
