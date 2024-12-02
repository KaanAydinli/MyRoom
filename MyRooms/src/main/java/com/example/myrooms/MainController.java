package com.example.myrooms;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.*;


public class MainController implements Initializable, Serializable {



    @FXML
    Pane myComputerPane;
    @FXML
    BorderPane myClockPane;
    @FXML
    LineChart<String, Number> myLineChart;
    @FXML
    BorderPane borderPane;
    @FXML
    ImageView myClock,myBookCase,myBoard,myCalendar,myAlarm,myPlant;
    @FXML
    Pane myClockParent;
    @FXML
    TextField dayTextField;
    @FXML
    TextField monthTextField;
    @FXML
    FlowPane mycorkBoardPane;
    @FXML
    Pane imagePane;
    @FXML
    Pane allPane;
    @FXML
    Pane alarmPane;
    @FXML
    TextField alarmText;
    @FXML
    Label totalLabel;
    @FXML
    TextArea books;
    @FXML
    TextField booktext;
    @FXML
    ToggleButton toggle;

    int totalCoin = 10;

    int total = 0;
    String alarmTime = "";
    String name;
    int totalTimeSpent;
    int difference = 0;


    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    public LinkedHashMap<String,String> UserDatabase = null;
    private final String  DataBase_FILE = "MyRooms/src/main/resources/UserDatabases/";

    public void setCoin(int money){
        totalCoin = money;
        totalLabel.setText(String.valueOf(totalCoin));
        UserDatabase.replace("TotalCoin", String.valueOf(totalCoin));
    }
    public void setAlarmImage(String image){
        myAlarm.setImage(new Image(image));
        UserDatabase.replace("Alarm", image);
    }
    public void setName(String names){
        name = names;
    }
    public void setBoardImage(String image){
        myBoard.setImage(new Image(image));
        UserDatabase.replace("Board", image);
    }
    public void setBookcaseImage(String image){
        myBookCase.setImage(new Image(image));
        UserDatabase.replace("Bookcase", image);
    }
    public void setCalendarImage(String image){
        myCalendar.setImage(new Image(image));
        UserDatabase.replace("Calendar", image);
    }
    public void setPlantImage(String image){
        myPlant.setImage(new Image(image));
        UserDatabase.replace("Plant", image);
    }
    public void setTotalTime(String time){
        totalTimeSpent = Integer.parseInt(time);
    }

    public void saveDatabase() {

        UserDatabase.replace("TotalCoin", totalCoin + "");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DataBase_FILE + name + "Database"))) {
            for (Map.Entry<String, String> entry : UserDatabase.entrySet()) {
                if(!entry.getValue().isEmpty()){
                    writer.write(entry.getKey() + " : " + entry.getValue());
                    writer.newLine();
                }
                else if(entry.getKey().isEmpty() || entry.getValue().isEmpty()){
                    writer.write(entry.getKey());
                    writer.newLine();
                }


            }
        } catch (IOException e) {
            System.err.println("Veritabanı kaydedilirken bir hata oluştu: " + e.getMessage());
        }
    }
    public void computerScene() {
        myClockParent.setVisible(false);
        myComputerPane.setVisible(!myComputerPane.isVisible());

    }
    public void chartScene() {
        myLineChart.setVisible(!myLineChart.isVisible());

    }
    public void clockScene() {
        myComputerPane.setVisible(false);
        myClockParent.setVisible(!myClockParent.isVisible());
    }
    public void alarmScene() {
        alarmPane.setVisible(!alarmPane.isVisible());
    }
    public void setAlarmText(String text){
        alarmText.setText(text);
    }
    public void createAlarm() {
        alarmTime = alarmText.getText();
        System.out.println("Alarm set to: " + alarmTime);
    }
    public void settingsScene() {
        totalCoin -= 30;
        buyClock("CsProject-BackGrounds/Clock3.png");
        setAlarmImage("CsProject-BackGrounds/Alarm2.png");
        setPlantImage("CsProject-BackGrounds/Plant2.png");

        totalLabel.setText(String.valueOf(totalCoin));
    }
    public void closeEveryPane(){
        myClockParent.setVisible(false);
        myComputerPane.setVisible(false);
        alarmPane.setVisible(false);
    }
    public void shopScene() {

    }
    public void musicScene() {

        AudioClip clip = new AudioClip(getClass().getResource("Sounds/librarySounds.mp3").toString());
        clip.play();
    }
    public void showAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alarm");
        alert.setHeaderText(null);
        alert.setContentText("Time is up");
    }
    public void playAudio(){
        AudioClip clip = new AudioClip(getClass().getResource("Sounds/alarmsound.wav").toString());
        clip.play();
    }
    public void buyClock(String imageString){
        Image image = new Image(imageString);
        UserDatabase.replace("Clock", imageString);
        myClock.setImage(image);
    }
    public void corkBoardScene(){
        Image image = new Image("CsProject-BackGrounds/postit.png");

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);

        UserDatabase.replace("Postits", String.valueOf(mycorkBoardPane.getChildren().size() + 1));

        if(mycorkBoardPane.getChildren().size() < 18){
            mycorkBoardPane.getChildren().add(imageView);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Mon", 0));
        series.getData().add(new XYChart.Data<>("Tue", 0));
        series.getData().add(new XYChart.Data<>("Wed", 0));
        series.getData().add(new XYChart.Data<>("Thu", 0));
        series.getData().add(new XYChart.Data<>("Fri", 0));
        series.getData().add(new XYChart.Data<>("Sat", 0));
        series.getData().add(new XYChart.Data<>("Sun", 0));
        myLineChart.getData().add(series);

        totalLabel.setText(String.valueOf(totalCoin));


        Light.Point light = new Light.Point();
        light.setX(750);
        light.setY(300);
        light.setZ(50);
        light.setColor(Color.BEIGE);

        Lighting lighting = new Lighting();
        lighting.setDiffuseConstant(2);
        lighting.setLight(light);


        //Setting light effects

//        imagePane.setEffect(lighting);
          //allPane.setEffect(lighting);
        //allPane.setEffect(new Lighting());

        dayTextField.setStyle(IDLE_BUTTON_STYLE);
        monthTextField.setStyle(IDLE_BUTTON_STYLE);
        myClockParent.setStyle(IDLE_BUTTON_STYLE);

        Clock clock1 = new Clock();
        Clock clock2 = new Clock();

        int startMinute = clock1.getMinute();


        dayTextField.setText(clock1.getDay() + "");
        monthTextField.setText(clock1.getMonth() + "");


        EventHandler<ActionEvent> ehandler = e->{
            clock1.setCurrent();


            if(alarmTime.equals(clock1.getTime())){
                showAlert();
                playAudio();
            }
            if(clock1.getMinute() - startMinute > difference){
                difference = clock1.getMinute() - startMinute;
                String temp = UserDatabase.get("TotalTimeSpent");
                UserDatabase.replace("TotalTimeSpent", (Integer.parseInt(temp) + 1) + "");
                UserDatabase.replace("LineChart_" + series.getData().get(clock1.getDayOfWeek()).getXValue(), ((int)series.getData().get(clock1.getDayOfWeek()).getYValue() + 1) + "");
                series.getData().get(clock1.getDayOfWeek()).setYValue((int)series.getData().get(clock1.getDayOfWeek()).getYValue() + 1);
            }

            saveDatabase();
            System.out.println(clock1.getTime());


            if(myClockParent.isVisible()){
                clock2.setCurrent();
            }

            total = clock1.getMinute() - startMinute;

        };

        System.out.println(clock1.getDate());

        Timeline anime = new Timeline(new KeyFrame(Duration.millis(1000),ehandler));
        anime.setCycleCount(Timeline.INDEFINITE);
        anime.play();


        // Construct a pane to hold both the elements.


        //borderPane.setCenter(calendar);
        borderPane.setCenter(clock1);
        myClockPane.setCenter(clock2);



    }
}