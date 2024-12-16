package com.example.myrooms;

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {



    @FXML
    Pane ComputerPane;
    @FXML
    BorderPane Clock2Pane;
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
    ImageView shopIcon;
    @FXML
    Slider settingsBrightnessSlider,settingsVolumeSlider;
    @FXML
    Pane settingsPane;
    @FXML
    ToggleButton settingsNotificationsButton;
    @FXML
    Pane ComputerApplications;
    @FXML
    Pane BoardPane, ChartPane;
    @FXML
    Pane AlarmNormalPane,AlarmPomodoroPane;
    @FXML
    ToggleButton alarmModeToggle;
    @FXML
    TextField alarmPomodoroSession,alarmPomodoroBreak;
    @FXML
    ProgressIndicator alarmProgress;
    @FXML
    Button alarmCreate;

    @FXML
    Label alarmTimeLabel;
    //azra
    @FXML
    Pane bookPane;
    @FXML
    Pane bookPane1;
    // azra
    @FXML
    Button addBookButton;
    //azra
    @FXML
    TextField bookTextField;
    //azra
    @FXML
    Label bookLabel;
    @FXML
    FlowPane flowPaneBook;
    @FXML
    FlowPane flowPaneBook2;
    @FXML
    FlowPane flowPaneBook3;
    @FXML
    FlowPane bookFlowPanee1;
    @FXML
    FlowPane bookFlowPanee2;
    @FXML
    FlowPane bookFlowPanee3;
    @FXML
    Button addImageButton;
    @FXML
    Pane addBookNamePane;
    @FXML
    TextField changePasswordTextfield;
    @FXML
    ImageView ComputerImage;
    @FXML
    Label totalTimeCharts,plantLevelCharts,totalHabitsCharts,totalPostitsCharts,totalBooksCharts;
    //zeynep
    @FXML
    Button plusButton;
    @FXML
    FlowPane SecondBoardPane;
    @FXML
    Pane ColorSelectFOrPostıtPane;
    @FXML
    Pane mainPostItPane;
    @FXML
    RadioButton yellowButton;
    @FXML
    RadioButton blueButton;
    @FXML
    RadioButton purpleButton;
    @FXML
    RadioButton GreenButton;
    @FXML
    Button backButtonOne;
    @FXML
    Button backButtonTwo;
    @FXML
    Button colorButtonOne;
    @FXML
    Button colorButtonTwo;
    @FXML
    TextField postItTitle;
    @FXML
    TextArea postItText;


    int totalCoin = 10;

    int total = 0;
    String alarmTime = "";
    String name;
    int totalTimeSpent;
    String bookName = "";

    StackPane stackPane;

    Timeline anime;
    Timeline lightingTimeline;

    Alarm alarm;
    Clock clock1;
    Clock clock2;
    BookCase bookcase;
    Board myBoard;

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";

    User user;

    public MainController() {

        name = LoginController.name;

        try {
            UserManager.USER_FILE = name + ".ser";
            user = UserManager.loadUser();
            clock1 = user.room.clock1;
            clock2 = user.room.clock2;
            alarm = user.room.alarm;
            bookcase = user.room.bookcase;
            totalCoin = user.totalCoin;
            totalTimeSpent = user.totalHours;
            myBoard = user.room.board;

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //azra
    public void addBook() {
        bookPane.setVisible(true);
        bookTextField.setOnAction(event -> {
            bookLabel.setText(bookTextField.getText());
        });
    }
    public void bookNameKey() {

            bookLabel.setText(bookTextField.getText());
            bookName = bookTextField.getText();
    }

    public void setShopIcon(String name){
        shopIcon.setImage(new Image(name));
    }
    public void setCoin(int money){
        totalCoin = money;
        TotalCoinLabel.setText(String.valueOf(totalCoin));

    }
    public void setAlarmImage(String image){
        AlarmImage.setImage(new Image(image));

    }
    public void setName(String names){
        name = names;
    }
    public void setBoardImage(String image){
        BoardImage.setImage(new Image(image));

    }
    public void setBookcaseImage(String image){
        BookcaseImage.setImage(new Image(image));

    }
    public void setCalendarImage(String image){
        CalendarImage.setImage(new Image(image));

    }
    public void setPlantImage(String image){
        PlantImage.setImage(new Image(image));

    }
    public void setTotalTime(int time){
        totalTimeSpent = time;
    }

    public void computerScene() {
        ClockParent.setVisible(false);
        ComputerPane.setVisible(!ComputerPane.isVisible());
        ComputerApplications.setVisible(!ComputerApplications.isVisible());

    }
    public void chartScene() {
        ComputerApplications.setVisible(false);
        ChartPane.setVisible(!ChartPane.isVisible());

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
    public void changeAlarmMode(){
        AlarmNormalPane.setVisible(!AlarmNormalPane.isVisible());
        AlarmPomodoroPane.setVisible(!AlarmPomodoroPane.isVisible());

        if(AlarmPomodoroPane.isVisible()){
            alarm.mode = 1;
            alarmModeToggle.setText("Pomodoro");
        }
        else {
            alarm.mode = 0;
            alarmModeToggle.setText("Normal");
        }
    }
    public void createAlarm() {

        if(alarmCreate.getText().equals("Start Alarm")){
            if(alarm.mode == 0){
                alarmTime = alarmText.getText();
                try {
                    String[] timeparts = alarmTime.split(":");
                    int hour = Integer.parseInt(timeparts[0]);
                    int minute = Integer.parseInt(timeparts[1]);
                    int seconds = Integer.parseInt(timeparts[2]);

                    alarm.createAlarmNormal(hour, minute, seconds);
                    alarm.setStartingTime(clock1.getTotalTime());

                    System.out.println("Alarm set to: " + alarm.getAlarmTime());
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Wrong or missing input in alarm");
                }
                catch (Exception e) {
                    System.out.println("Error in alarm creation");
                }
            }
            else if(alarm.mode == 1){

                try {
                    int session = Integer.parseInt(alarmPomodoroSession.getText());
                    int breakTime = Integer.parseInt(alarmPomodoroBreak.getText());
                    alarm.createAlarmPomodoro(session, breakTime);
                    System.out.println("Alarm set to: " + alarm.getAlarmTime());
                } catch (Exception e) {
                    System.out.println("Wrong or missing input in alarm");
                }
            }
            alarmCreate.setText("Stop Alarm");
            alarmTimeLabel.setText(alarmTimeLabel.getText() + alarmTime);
        }
        else if(alarmCreate.getText().equals("Stop Alarm")){
            deActiveAlarm();
            alarmCreate.setText("Start Alarm");
        }
    }
    public void settingsScene() {
        ComputerApplications.setVisible(false);
        settingsPane.setVisible(!settingsPane.isVisible());

    }
    public void closeEveryPane(){
        ClockParent.setVisible(false);
        ComputerPane.setVisible(false);
        AlarmPane.setVisible(false);
        settingsPane.setVisible(false);
        ChartPane.setVisible(false);
        ComputerApplications.setVisible(false);
        BoardPane.setVisible(false);
        bookPane.setVisible(false);
        addBookNamePane.setVisible(false);
        mainPostItPane.setVisible(false);
        ColorSelectFOrPostıtPane.setVisible(false);

    }
    public void shopScene() {

    }
    public void changePass(){

        user.password = changePasswordTextfield.getText();
        LoginController.database.put(name,user.password);
        LoginController.saveDatabase();
    }
    public void musicScene() {

        AudioClip clip = new AudioClip(getClass().getResource("com/example/myrooms/Sounds/librarySounds.mp3").toString());
        clip.play();
    }
    public void showAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alarm");
        alert.setHeaderText(null);
        alert.setContentText("Time is up");
        alert.show();
    }
    public void playAudio(){
        AudioClip clip = new AudioClip(getClass().getResource("com/example/myrooms/Sounds/alarmsound.wav").toString());
        clip.play();
    }
    public void buyClock(String imageString){
        Image image = new Image(imageString);

        ClockImage.setImage(image);
    }
    public void checkAlarm(int hour,int minute, int seconds){

        if (alarm.checkAlarm(hour,minute,seconds)) {
            playAudio();
            showAlert();
        }
    }
    public void deActiveAlarm(){
        alarm.deactivateAlarm();
    }

    public void corkBoardScene(){
        BoardPane.setVisible(true);
    }
    public void colorSelected()
    {
        int number;
        number = myBoard.currentColor;
        myBoard.currentPostIt.setColor(number);
        printPostIts();
        ColorSelectFOrPostıtPane.setVisible(false);
    }

    public void greenRadioButtonClicked()
    {
        myBoard.currentColor = 3;

    }
    public void yellowRadioButtonClicked()
    {
        myBoard.currentColor = 1;



    }
    public void purpleRadioButtonClicked()
    {
        myBoard.currentColor = 2;


    }
    public void blueRadioButtonClicked()
    {
        myBoard.currentColor = 4;


    }
    public void saveTextAndTitle()
    {
        myBoard.currentPostIt.setTitle(postItTitle.getText());
        myBoard.currentPostIt.setText(postItText.getText());
        printPostIts();
        mainPostItPane.setVisible(false);


    }
    public void closeCurrentPostit(){
        mainPostItPane.setVisible(false);
    }

    public void plusIconsMethod() {


        if (CorkboardPostitsPane.getChildren().size() < 24) {
            PostIt b = new PostIt("Title", "Text", 0);
            b.getChildren().clear();
            b.setPrefHeight(79);
            b.setPrefWidth(87);

            b.setStyle(" -fx-background-color: BEIGE");
            TextField textOne = new TextField(b.getTitle());
            textOne.setEditable(false);

            b.setAlignment(textOne, Pos.CENTER);
            TextField textTwo = new TextField(b.getText());
            //b.setAlignment(textTwo, Pos.CENTER);
            //String nameOfThePostItColor = colorOfPostIt.toString();

            textOne.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-width: 1;");
            //textOne.setEditable(false);
            //textTwo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-width: 1;");
            //textTwo.setEditable(false);

            b.getChildren().add(textOne);
            //b.getChildren().add(textTwo);
            PostIt c = b.minimizePostIt();
            myBoard.postItArrayList.add(b);
            CorkboardPostitsPane.getChildren().add(c);
            SecondBoardPane.getChildren().add(b);

            b.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    myBoard.currentPostIt = b;
                    mainPostItPane.setVisible(true);
                    postItTitle.setText(b.getTitle());
                    postItText.setText(b.getText());

                    printPostIts();
                }
            });


        }
    }
    public void colorTwoClicked()
    {
        mainPostItPane.setVisible(false);
        ColorSelectFOrPostıtPane.setVisible(true);

    }

    public void printPostIts()
    {
        SecondBoardPane.getChildren().clear();
        CorkboardPostitsPane.getChildren().clear();

        int length = myBoard.getPostItArrayList().size();


        for(int i =0; i< length; i++)
        {
            if(myBoard.getPostItArrayList().get(i) != null)
            {
                myBoard.getPostItArrayList().get(i).getChildren().clear();
                myBoard.getPostItArrayList().get(i).setPrefHeight(79);
                myBoard.getPostItArrayList().get(i).setPrefWidth(87);


                if(myBoard.getPostItArrayList().get(i).getColor()==0)
                {
                    myBoard.getPostItArrayList().get(i).setStyle(" -fx-background-color: BEIGE");
                }
                else if(myBoard.getPostItArrayList().get(i).getColor()==1){
                    myBoard.getPostItArrayList().get(i).setStyle(" -fx-background-color: #d3d30f");
                }
                else if(myBoard.getPostItArrayList().get(i).getColor()==2)
                {
                    myBoard.getPostItArrayList().get(i).setStyle(" -fx-background-color: #7e5c7e");
                }
                else if(myBoard.getPostItArrayList().get(i).getColor()==3){
                    myBoard.getPostItArrayList().get(i).setStyle(" -fx-background-color: #70a670");
                }
                else if(myBoard.getPostItArrayList().get(i).getColor()==4){
                    myBoard.getPostItArrayList().get(i).setStyle(" -fx-background-color: #0101e4");
                }





                TextField textOne = new TextField( myBoard.getPostItArrayList().get(i).getTitle());
                textOne.setEditable(false);

                myBoard.getPostItArrayList().get(i).setAlignment(textOne, Pos.CENTER);
                //TextField textTwo = new TextField( myBoard.getPostItArrayList().get(i).getText());
                //myBoard.getPostItArrayList().get(i).setAlignment(textTwo, Pos.CENTER);
                //String nameOfThePostItColor = colorOfPostIt.toString();

                textOne.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-width: 1;");
                //textTwo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-width: 1;");

                myBoard.getPostItArrayList().get(i).getChildren().add(textOne);
                // myBoard.getPostItArrayList().get(i).getChildren().add(textTwo);


                SecondBoardPane.getChildren().add(myBoard.getPostItArrayList().get(i));
                CorkboardPostitsPane.getChildren().add(myBoard.getPostItArrayList().get(i).minimizePostIt());


            }
        }

    }
    public void deleteThePostıt()
    {
        myBoard.deletePostIt(myBoard.currentPostIt);
        mainPostItPane.setVisible(false);
        printPostIts();

    }
    public void backToTheBoard()
    {
        mainPostItPane.setVisible(false);
        ColorSelectFOrPostıtPane.setVisible(false);

    }

    public void goToLogin() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginRoom.fxml"));
        Parent root = loader.load();


        anime.stop();
        lightingTimeline.stop();



        Timeline scaleOut = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(allPane.scaleXProperty(), 1),
                        new KeyValue(allPane.scaleYProperty(), 1)
                ),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(allPane.scaleXProperty(), 5),
                        new KeyValue(allPane.scaleYProperty(), 5)
                )
        );

        scaleOut.setOnFinished(event -> {
            stackPane.getChildren().add(root);
            stackPane.getChildren().remove(allPane);

            Timeline scaleIn = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new KeyValue(root.scaleXProperty(), 5),
                            new KeyValue(root.scaleYProperty(), 5)
                    ),
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(root.scaleXProperty(), 1),
                            new KeyValue(root.scaleYProperty(), 1)
                    )
            );
            scaleIn.play();
        });

        scaleOut.play();
    }
    public void bookCaseScene() {

       bookPane.setVisible(!bookPane.isVisible());

    }
    public void addImage(){

        String imagePath ="CsProject-BackGrounds/Book.png";
        if(!bookName.equals("")){
            totalBooksCharts.setText((Integer.parseInt(totalBooksCharts.getText()) + 1)+ "");
            Book book = new Book(bookTextField.getText(),  imagePath);
            int count = bookcase.getCount();
            bookcase.books[bookcase.getCount()] = book;
            bookcase.setCount(++count);

            putAndSortBooks();


            bookName = "";
            bookTextField.clear();
        }
        addBookNamePane.setVisible(!addBookNamePane.isVisible());

       // bookPane.setVisible(!bookPane.isVisible());

    }
    public void putAndSortBooks(){
        bookcase.sortBooks();

        bookFlowPanee1.getChildren().clear();
        bookFlowPanee2.getChildren().clear();
        bookFlowPanee3.getChildren().clear();

        flowPaneBook.getChildren().clear();
        flowPaneBook2.getChildren().clear();
        flowPaneBook3.getChildren().clear();

        for(Book b : bookcase.books){

            if(b != null){
                ImageView bookImageView = new ImageView(new Image(b.path));
                bookImageView.setFitWidth(25);
                bookImageView.setFitHeight(110);
                addImageViewOfBooks();


                b.setPrefSize(bookImageView.getFitWidth()+5, bookImageView.getFitHeight());
                Label bookLabel = new Label(b.bookName);
                bookLabel.setWrapText(true);
                bookLabel.setPrefWidth(150);
                bookLabel.setTranslateY(-(bookImageView.getFitHeight()/2) +20);
                bookLabel.setStyle(" -fx-background-color: transparent; " +
                        "-fx-text-fill: white; -fx-font-size: 10px;-fx-text-overrun: clip;");
                bookLabel.setRotate(90);
                b.getChildren().addAll(bookImageView, bookLabel);


                if(bookFlowPanee1.getChildren().size()<9){
                    bookFlowPanee1.getChildren().add(b);
                }
                else if(bookFlowPanee2.getChildren().size()<10){
                    bookFlowPanee2.getChildren().add(b);
                }
                else if(bookFlowPanee3.getChildren().size()<10){
                    bookFlowPanee3.getChildren().add(b);
                }
            }
        }
    }
    public  void addImageViewOfBooks(){
        Image image = new Image("CsProject-BackGrounds/Book.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(60);
        imageView.setFitWidth(25);
        imageView.setPreserveRatio(true);
        if(flowPaneBook.getChildren().size()<9){
            flowPaneBook.getChildren().add(imageView);

        }
        else if(flowPaneBook2.getChildren().size()<10){
            flowPaneBook2.getChildren().add(imageView);
        }
        else if(flowPaneBook3.getChildren().size()<10){
            flowPaneBook3.getChildren().add(imageView);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        name = LoginController.name;


        try {
            UserManager.USER_FILE = name + ".ser";
            user = UserManager.loadUser();
            clock1 = user.room.clock1;
            clock2 = user.room.clock2;
            alarm = user.room.alarm;
            bookcase = user.room.bookcase;
            totalCoin = user.totalCoin;
            totalTimeSpent = user.totalHours;

        } catch (Exception e) {
            e.printStackTrace();
        }
        setAlarmImage(alarm.imagePath);
        buyClock(clock1.imagePath);
        setCoin(totalCoin);
        setTotalTime(totalTimeSpent);
        putAndSortBooks();
        printPostIts();
        totalTimeCharts.setText(user.totalHours + "");

        Image image = new Image("CsProject-BackGrounds/ComputerCursor.png");
        ToggleGroup group = new ToggleGroup();
        yellowButton.setToggleGroup(group);
        blueButton.setToggleGroup(group);
        GreenButton.setToggleGroup(group);
        purpleButton.setToggleGroup(group);

        ComputerPane.setCursor(new ImageCursor(image,
                image.getWidth() ,
                image.getHeight() ));
        settingsPane.setCursor(new ImageCursor(image,
                image.getWidth() ,
                image.getHeight() ));
        ChartPane.setCursor(new ImageCursor(image,
                image.getWidth() ,
                image.getHeight() ));

        settingsBrightnessSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                ColorAdjust setBrightness = new ColorAdjust();
                setBrightness.setBrightness(settingsBrightnessSlider.getValue());
                allPane.setEffect(setBrightness);
            }
        });

        TotalCoinLabel.setText(String.valueOf(totalCoin));

        Light.Point light = new Light.Point();
        light.setX(50);
        light.setY(300);
        light.setZ(200);
        light.setColor(Color.BEIGE);

        Lighting lightingEffect = new Lighting();
        lightingEffect.setLight(light);
        lightingEffect.setDiffuseConstant(10);

        allPane.setEffect(lightingEffect);

        lightingTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(light.colorProperty(), Color.BEIGE)),
                new KeyFrame(Duration.seconds(10), new KeyValue(light.colorProperty(), Color.rgb(225, 215, 180))), // Afternoon light
                new KeyFrame(Duration.seconds(30), new KeyValue(light.colorProperty(), Color.rgb(50, 50, 150))),  // Night light
                new KeyFrame(Duration.seconds(50), new KeyValue(light.colorProperty(), Color.rgb(225, 215, 180)))
        );

        lightingTimeline.setCycleCount(Timeline.INDEFINITE);
        lightingTimeline.play();

        dayTextField.setStyle(IDLE_BUTTON_STYLE);
        monthTextField.setStyle(IDLE_BUTTON_STYLE);
        ClockParent.setStyle(IDLE_BUTTON_STYLE);

        int startMinute = clock1.getMinute();
        alarmProgress.setProgress(0);

        dayTextField.setText(clock1.getDay() + "");
        monthTextField.setText(clock1.getMonth() + "");

        EventHandler<ActionEvent> ehandler = e->{
            clock1.setCurrent();

            checkAlarm(clock1.getHour(),clock1.getMinute(),clock1.getSecond());

            if(alarm.getTotalTime() != 0){

                int progress = alarm.compareTo(alarm.getStartingTime());
                int comparison = clock1.compareTo(alarm.getTotalTime());
                if(alarm.isOn){
                    float value = (float) (100 - (comparison * 100 / progress)) / 100f;
                    alarmProgress.setProgress(value);
                }
                else{
                    alarmProgress.setProgress(0);
                    alarmTimeLabel.setText("Alarm Time: ");
                }
            }
            UserManager.saveUser(user);

            System.out.println(user.password);
            System.out.println(clock1.getTime());

            if(ClockParent.isVisible()){
                clock2.setCurrent();
            }

            total = clock1.getMinute() - startMinute;

        };

        System.out.println(clock1.getDate());

        anime = new Timeline(new KeyFrame(Duration.millis(1000),ehandler));
        anime.setCycleCount(Timeline.INDEFINITE);
        anime.play();

        //borderPane.setCenter(calendar);
        Clock1Pane.setCenter(clock1);
        Clock2Pane.setCenter(clock2);

    }
}