package com.example.myrooms;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import java.util.ResourceBundle;
import java.util.*;


public class MainController implements Initializable, Serializable {



    @FXML
    Pane ComputerPane;
    @FXML
    BorderPane Clock2Pane;
    @FXML
    LineChart<String, Number> LineChart;
    @FXML
    BorderPane Clock1Pane;
    @FXML
    ImageView ClockImage, BookcaseImage, BoardImage, CalendarImage, AlarmImage, PlantImage;
    @FXML
    Pane ClockParent;
    @FXML
    TextField dayTextField;
    @FXML
    TextField monthTextField;
    @FXML
    FlowPane CorkboardPostitsPane;
    @FXML
    Pane imagePane;
    @FXML
    Pane allPane;
    @FXML
    Pane AlarmPane;
    @FXML
    TextField alarmText;
    @FXML
    Label TotalCoinLabel;
    @FXML
    TextArea books;
    @FXML
    TextField booktext;
    @FXML
    ToggleButton toggle;
    @FXML
    ImageView shopIcon;

    int totalCoin = 10;

    int total = 0;
    String alarmTime = "";
    String name;
    int totalTimeSpent;
    int difference = 0;

    Alarm alarm;
    Clock clock1;
    Clock clock2;


    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    public LinkedHashMap<String,String> UserDatabase = null;
    private final String  DataBase_FILE = "MyRooms/src/main/resources/UserDatabases/";


    public MainController() {
        alarm = new Alarm();
        clock1 = new Clock();
        clock2 = new Clock();
    }
    public void setShopIcon(String name){
        shopIcon.setImage(new Image(name));
    }
    public void setCoin(int money){
        totalCoin = money;
        TotalCoinLabel.setText(String.valueOf(totalCoin));
        UserDatabase.replace("TotalCoin", String.valueOf(totalCoin));
    }
    public void setAlarmImage(String image){
        AlarmImage.setImage(new Image(image));
        UserDatabase.replace("Alarm", image);
    }
    public void setName(String names){
        name = names;
    }
    public void setBoardImage(String image){
        BoardImage.setImage(new Image(image));
        UserDatabase.replace("Board", image);
    }
    public void setBookcaseImage(String image){
        BookcaseImage.setImage(new Image(image));
        UserDatabase.replace("Bookcase", image);
    }
    public void setCalendarImage(String image){
        CalendarImage.setImage(new Image(image));
        UserDatabase.replace("Calendar", image);
    }
    public void setPlantImage(String image){
        PlantImage.setImage(new Image(image));
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
        ClockParent.setVisible(false);
        ComputerPane.setVisible(!ComputerPane.isVisible());

    }
    public void chartScene() {
        LineChart.setVisible(!LineChart.isVisible());

    }
    public void clockScene() {
        ComputerPane.setVisible(false);
        ClockParent.setVisible(!ClockParent.isVisible());
    }
    public void alarmScene() {
        AlarmPane.setVisible(!AlarmPane.isVisible());
    }
    public void setAlarmText(String text){
        alarmText.setText(text);
    }
    public void createAlarm() {
        alarmTime = alarmText.getText();
        String[] timeparts = alarmTime.split(":");
        int hour = Integer.parseInt(timeparts[0]);
        int minute = Integer.parseInt(timeparts[1]);
        int seconds = Integer.parseInt(timeparts[2]);

        alarm.createAlarmNormal(hour, minute, seconds);

        System.out.println("Alarm set to: " + alarm.getAlarmTime());
    }
    public void settingsScene() {
        totalCoin -= 30;
        buyClock("CsProject-BackGrounds/Clock3.png");
        setAlarmImage("CsProject-BackGrounds/Alarm2.png");
        setPlantImage("CsProject-BackGrounds/Plant2.png");

        TotalCoinLabel.setText(String.valueOf(totalCoin));
    }
    public void closeEveryPane(){
        ClockParent.setVisible(false);
        ComputerPane.setVisible(false);
        AlarmPane.setVisible(false);
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
        ClockImage.setImage(image);
    }
    public void checkAlarm(int hour,int minute, int seconds){

        if (alarm.checkAlarm(hour,minute,seconds)) {
            playAudio();
            showAlert();
            alarm.deactivateAlarm();
        }
    }
    public void corkBoardScene(){
        Image image = new Image("CsProject-BackGrounds/postit.png");

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);

        UserDatabase.replace("Postits", String.valueOf(CorkboardPostitsPane.getChildren().size() + 1));

        if(CorkboardPostitsPane.getChildren().size() < 18){
            CorkboardPostitsPane.getChildren().add(imageView);
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
        LineChart.getData().add(series);

        TotalCoinLabel.setText(String.valueOf(totalCoin));


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
        ClockParent.setStyle(IDLE_BUTTON_STYLE);

        int startMinute = clock1.getMinute();


        dayTextField.setText(clock1.getDay() + "");
        monthTextField.setText(clock1.getMonth() + "");

        EventHandler<ActionEvent> ehandler = e->{
            clock1.setCurrent();

            checkAlarm(clock1.getHour(),clock1.getMinute(),clock1.getSecond());

            saveDatabase();
            System.out.println(clock1.getTime());

            if(ClockParent.isVisible()){
                clock2.setCurrent();
            }

            total = clock1.getMinute() - startMinute;

        };

        System.out.println(clock1.getDate());

        Timeline anime = new Timeline(new KeyFrame(Duration.millis(1000),ehandler));
        anime.setCycleCount(Timeline.INDEFINITE);
        anime.play();

        //borderPane.setCenter(calendar);
        Clock1Pane.setCenter(clock1);
        Clock2Pane.setCenter(clock2);

    }
}