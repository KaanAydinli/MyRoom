package com.example.myrooms;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.PointLight;
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
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.*;
import java.util.*;


public class MainController implements Initializable {


    private static final String DATABASE_FILE = "database.txt";
    private static final Map<String, List<String>> qaDatabase = new HashMap<>();

    @FXML
    Pane myComputerPane;
    @FXML
    BorderPane myClockPane;
    @FXML
    LineChart<String, Number> myLineChart;
    @FXML
    BorderPane borderPane;
    @FXML
    ImageView myClock;
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

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";



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
    public void createAlarm() {
        alarmTime = alarmText.getText();
        System.out.println("Alarm set to: " + alarmTime);
    }
    public void settingsScene() {
        totalCoin -= 30;
        buyClock();
        totalLabel.setText(String.valueOf(totalCoin));

    }
    public void closeEveryPane(){
        myClockParent.setVisible(false);
        myComputerPane.setVisible(false);
        alarmPane.setVisible(false);
    }
    public void musicScene() {
        AudioClip clip = new AudioClip(getClass().getResource("Sounds/librarySounds.mp3").toString());
        clip.play();
    }
    public void showAlert(){
        alert.show();
    }
    public void playAudio(){
        AudioClip clip = new AudioClip(getClass().getResource("Sounds/alarmsound.wav").toString());
        clip.play();
    }
    public void buyClock(){
        Image image = new Image("CsProject-BackGrounds/Clock2.png");
        myClock.setImage(image);
    }
    public void corkBoardScene(){
        Image image = new Image("CsProject-BackGrounds/postit.png");

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        totalCoin++;
        totalLabel.setText(String.valueOf(totalCoin));

        if(mycorkBoardPane.getChildren().size() < 18){
            mycorkBoardPane.getChildren().add(imageView);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Mon", 1));
        series.getData().add(new XYChart.Data<>("Tue", 2));
        series.getData().add(new XYChart.Data<>("Wed", 1));
        series.getData().add(new XYChart.Data<>("Thu", 2.5));
        series.getData().add(new XYChart.Data<>("Fri", 2.8));
        series.getData().add(new XYChart.Data<>("Sat", 1.6));
        series.getData().add(new XYChart.Data<>("Sun", 1));
        myLineChart.getData().add(series);

        totalLabel.setText(String.valueOf(totalCoin));

        alert.setContentText("Alarm!");
        alert.setHeaderText("");
        alert.setTitle("Alarm");


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
            }

            if(alert.isShowing()){
                playAudio();
            }


            System.out.println(clock1.getTime());


            if(myClockParent.isVisible()){
                clock2.setCurrent();
            }

            total = clock1.getMinute() - startMinute;

            series.getData().getFirst().setYValue(total);
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