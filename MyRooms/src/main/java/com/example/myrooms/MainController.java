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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    @FXML
    Pane musicAppPane;
    @FXML
    Button skipButton;
    @FXML
    Button previousButton;
    @FXML
    ImageView pauseImage;
    @FXML
    ImageView resumeImage;
    @FXML
    TextField SongNameArea;
    @FXML
    Button firstSoundsButton;
    @FXML
    Button secondSoundsButton;
    @FXML
    Button thirdSoundsButton;
    @FXML
    Button fourthSoundsButton;
    @FXML
    Button fifthSoundsButton;
    @FXML
    Button sixthSoundsButton;
    @FXML
    Button seventhSoundsButton;
    @FXML
    ImageView BigPlantImage,WaterCanImage;
    @FXML
    Pane PlantPane;
    @FXML
    ScrollPane ShopPane;
    @FXML
    Pane ShopPaneInPane,BuySoundPane,buyPostItPane;
    @FXML
    ImageView clock2png,standartCalender, StandartAlarm,alarm2png,board2, standartBoard,standartClock, standartNotebook, GFImage,CelloImage,LoftImage,notebook4,notebook3,notebook2,clock3,calendar4,calendar3, alarm4,alarm3, calender2, yellowwpostit, bluepostit,greenpostit,purplepostit;
    @FXML
    CheckBox GodfatherThemeCB, LoftMusicCB , CelloAndPianoCB,yellowPostItCB,bluePostItCB,purplePostItCB,greenPostItCB;
    @FXML
    RadioButton standartAlarmRB,alarm2RB,clock2RB,standartClockRB, calender2RB,standartBoardRB,standartCalenderRB,standartNoteBookRB,board2RB, alarm4RB,calendar3RB,calendar4RB,clock3RB,notebook3RB,notebook2RB,notebook4RB,alarm3RB;
    @FXML
    Button p,s,returnShopButton;
    @FXML
    ImageView bigClockImage, bigBoardImage, bigBookcase,bigAlarm, NoteBookImage;
    @FXML
    TextArea godFatherText;
    @FXML
    TextArea celloAndPianoText;
    @FXML
    TextArea loftMusicText;


    Integer totalCoin;

    int total = 0;
    String alarmTime = "";
    String name;
    Integer totalTimeSpent;
    String bookName = "";
    double volume = 50;
    AudioClip aclip;
    Media media = new Media(getClass().getResource("/com/example/myrooms/Sounds/NatureSound.mp3").toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);

    StackPane stackPane;

    Timeline anime;
    Timeline lightingTimeline;

    Alarm alarm;
    Clock clock1;
    Clock clock2;
    BookCase bookcase;
    Board myBoard;
    Plant plant;
    Notebooks notebooks;
    Calendars calendars;
    Shop shop;
    Settings settings;
    MusicPlayer myMusicPlayer;
    AudioClip clip;

    Notebook notebook;
    CalendarController calendarController;

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";

    User user;
    boolean isAdmin;


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
            plant = user.room.plant;
            notebooks = user.room.notebooks;
            calendars = user.room.calendars;
            shop = user.room.shop;
            settings = user.room.settings;
            myMusicPlayer = user.room.musicPlayer;

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
    public void setNotify(){

        if(settingsNotificationsButton.getText().equals("On")){
            settingsNotificationsButton.setText("Off");
            alarm.notify = false;
        }
        else{
            settingsNotificationsButton.setText("On");
            alarm.notify = true;
        }
    }

    public void bookNameKey() {

        bookLabel.setText(bookTextField.getText());
        bookName = bookTextField.getText();
    }
    public void loadShop(){

        for(int i = 0; i < shop.items.size(); i++){

            if(shop.items.get(i).name.equals("Calendar")){
                if(shop.items.get(i).isUnlocked){
                    standartCalenderRB.setDisable(false);
                }
                else
                    standartCalenderRB.setDisable(true);

            }
            else if(shop.items.get(i).name.equals("Calendar2")){
                if(shop.items.get(i).isUnlocked){
                    calender2RB.setDisable(false);
                }
                else
                    calender2RB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Calendar3")){
                if(shop.items.get(i).isUnlocked){
                    calendar3RB.setDisable(false);
                }
                else
                    calendar3RB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Calendar4")){
                if(shop.items.get(i).isUnlocked){
                    calendar4RB.setDisable(false);
                }
                else
                    calendar4RB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Clock1")){
                if(shop.items.get(i).isUnlocked){
                    standartClockRB.setDisable(false);
                }
                else
                    standartClockRB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Clock2")){
                if(shop.items.get(i).isUnlocked){
                    clock2RB.setDisable(false);
                }
                else
                    clock2RB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Clock3")){
                if(shop.items.get(i).isUnlocked){
                    clock3RB.setDisable(false);
                }
                else
                    clock3RB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Board1")){
                if(shop.items.get(i).isUnlocked){
                    standartBoardRB.setDisable(false);
                }
                else
                    standartBoardRB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Board2")){
                if(shop.items.get(i).isUnlocked){
                    board2RB.setDisable(false);
                }
                else
                    board2RB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Notebook1")){
                if(shop.items.get(i).isUnlocked){
                    standartNoteBookRB.setDisable(false);
                }
                else
                    standartNoteBookRB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Notebook2")){
                if(shop.items.get(i).isUnlocked){
                    notebook2RB.setDisable(false);
                }
                else
                    notebook2RB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Notebook3")){
                if(shop.items.get(i).isUnlocked){
                    notebook3RB.setDisable(false);
                }
                else
                    notebook3RB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Notebook4")){
                if(shop.items.get(i).isUnlocked){
                    notebook4RB.setDisable(false);
                }
                else
                    notebook4RB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("GodFather")){
                if(shop.items.get(i).isUnlocked){
                    GodfatherThemeCB.setDisable(false);
                }
                else
                    GodfatherThemeCB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Loft")){
                if(shop.items.get(i).isUnlocked){
                    LoftMusicCB.setDisable(false);
                }
                else
                    LoftMusicCB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Cello")){
                if(shop.items.get(i).isUnlocked){
                    CelloAndPianoCB.setDisable(false);
                }
                else
                    CelloAndPianoCB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Green")){
                if(shop.items.get(i).isUnlocked){
                    greenPostItCB.setDisable(false);
                }
                else
                    greenPostItCB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Alarm1")){
                if(shop.items.get(i).isUnlocked){
                    standartAlarmRB.setDisable(false);
                }
                else
                    standartAlarmRB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Alarm2")){
                if(shop.items.get(i).isUnlocked){
                    alarm2RB.setDisable(false);
                }
                else
                    alarm2RB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Alarm3")){
                if(shop.items.get(i).isUnlocked){
                    alarm3RB.setDisable(false);
                }
                else
                    alarm3RB.setDisable(true);
            }
            else if(shop.items.get(i).name.equals("Alarm4")){
                if(shop.items.get(i).isUnlocked){
                    alarm4RB.setDisable(false);
                }
                else
                    alarm4RB.setDisable(true);
            }



        }
    }
    public void setShopIcon(String name){
        shopIcon.setImage(new Image(name));
    }
    public void setCoin(int money){
        totalCoin = money;
        TotalCoinLabel.setText(String.valueOf(totalCoin));

    }
    public void setNotebook(String image){
        NoteBookImage.setImage(new Image(image));
    }
    public void setName(String names){
        name = names;
    }
    public void setCalendarImage(String image){
        CalendarImage.setImage(new Image(image));
    }
    public void setAlarmImage(String image){
        AlarmImage.setImage(new Image(image));
        bigAlarm.setImage(new Image(image));
    }
    public void setBookcaseImage(String image){
        BookcaseImage.setImage(new Image(image));
        bigBookcase.setImage(new Image(image));
    }
    public void setClockImage(String image){

        ClockImage.setImage(new Image(image));
        bigClockImage.setImage(new Image(image));
    }
    public void setBoardImage(String image){
        BoardImage.setImage(new Image(image));
        bigBoardImage.setImage(new Image(image));
    }
    public void setTotalTime(int time){
        totalTimeSpent = time;
    }

    public void computerScene() {
        if(isAdmin){
            ClockParent.setVisible(false);
            ComputerPane.setVisible(!ComputerPane.isVisible());
            ComputerApplications.setVisible(!ComputerApplications.isVisible());
        }
    }
    public void chartScene() {
        totalTimeCharts.setText(String.valueOf(totalTimeSpent));
        totalBooksCharts.setText(String.valueOf(bookcase.getCount()));
        ComputerApplications.setVisible(false);
        ChartPane.setVisible(!ChartPane.isVisible());

    }
    public void clockScene() {
        if(isAdmin){
            ComputerPane.setVisible(false);
            ClockParent.setVisible(!ClockParent.isVisible());
        }

    }
    public void alarmScene() {
        if(isAdmin){
            AlarmPane.setVisible(!AlarmPane.isVisible());
        }

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
                    alarmCreate.setText("Stop Alarm");
                    alarmTimeLabel.setText(alarmTimeLabel.getText() + alarmTime);
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
                    alarmCreate.setText("Stop Alarm");
                    alarmTimeLabel.setText(alarmTimeLabel.getText() + alarmTime);
                } catch (Exception e) {
                    System.out.println("Wrong or missing input in alarm");
                }
            }

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
    //TODO
    public void InitializeCalendarNotebook()
    {
        calendarController = new CalendarController(allPane,user.ID);
        notebook = new Notebook(allPane, user.ID, calendarController.getToday());
    }

    public void openNotebook(ActionEvent actionEvent)
    {
        System.out.println("dsagydhujısaopğ");
        notebook.notebookOpen();

    }

    public void openCalendar(ActionEvent event)
    {
        calendarController.openCalendar();
        event.consume();
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
        musicAppPane.setVisible(false);
        PlantPane.setVisible(false);
        ShopPane.setVisible(false);
        ShopPaneInPane.setVisible(false);
        buyPostItPane.setVisible(false);
        BuySoundPane.setVisible(false);
        notebook.notebookClose();
        calendarController.closeCalendar();

    }
    public void shopScene() {
        ComputerApplications.setVisible(false);
        ShopPane.setVisible(true);
        ShopPaneInPane.setVisible(true);
    }
    public void changePass(){

        user.password = changePasswordTextfield.getText();
        LoginController.database.put(name,user.password);
        LoginController.saveDatabase();
    }
    public void musicScene() {
        musicAppPane.setVisible(true);

    }
    public void resumeButtonClicked()
    {
        resumeImage.setVisible(false);
        pauseImage.setVisible(true);

        if (myMusicPlayer.getCurrentMusic().getId() == 5)
        {
            clip = new AudioClip(getClass().getResource("/com/example/myrooms/Sounds/CelloAndPiano.mp3").toString());
            clip.play();
        }
        else if (myMusicPlayer.getCurrentMusic().getId() == 4)
        {
            clip = new AudioClip(getClass().getResource("/com/example/myrooms/Sounds/GodfatherTheme.mp3").toString());
            clip.play();
        }
        else if (myMusicPlayer.getCurrentMusic().getId() == 0)
        {
            clip = new AudioClip(getClass().getResource("/com/example/myrooms/Sounds/LibrarySound.mp3").toString());
            clip.play();
        }
        else if (myMusicPlayer.getCurrentMusic().getId() == 6)
        {
            clip = new AudioClip(getClass().getResource("/com/example/myrooms/Sounds/LoftMusic.mp3").toString());
            clip.play();
        }
        else if (myMusicPlayer.getCurrentMusic().getId() == 1)
        {
            clip = new AudioClip(getClass().getResource("/com/example/myrooms/Sounds/NatureSound.mp3").toString());
            clip.play();
        }
        else if (myMusicPlayer.getCurrentMusic().getId() == 2)
        {
            clip = new AudioClip(getClass().getResource("/com/example/myrooms/Sounds/RainSound.mp3").toString());
            clip.play();
        }
        else if (myMusicPlayer.getCurrentMusic().getId() == 3)
        {
            clip = new AudioClip(getClass().getResource("/com/example/myrooms/Sounds/RelaxingMusic.mp3").toString());
            clip.play();
        }
        SongNameArea.setText(myMusicPlayer.getCurrentMusic().getName());

    }

    public void firstSoundButtonClicked()
    {
        myMusicPlayer.setCurrentMusic("Library Sound");
        SongNameArea.setText("Library Sound");
    }
    public void secondSoundClicked()
    {
        myMusicPlayer.setCurrentMusic("Nature Sound");
        SongNameArea.setText("Nature Sound");
    }
    public void thirdSoundClicked()
    {
        myMusicPlayer.setCurrentMusic("Rain Sound");
        SongNameArea.setText("Rain Sound");
    }
    public void fourthSoundClicked()
    {
        myMusicPlayer.setCurrentMusic("Relaxing Sound");
        SongNameArea.setText("Relaxing Sound");
    }
    public void fifthSoundClicked()
    {
        myMusicPlayer.setCurrentMusic("Godfather Theme");
        SongNameArea.setText("Godfather Theme");
    }
    public void sixthSoundClicked()
    {
        myMusicPlayer.setCurrentMusic("Cello And Piano");
        SongNameArea.setText("Cello And Piano");
    }
    public void seventhSoundClicked()
    {
        myMusicPlayer.setCurrentMusic("Loft Music");
        SongNameArea.setText("Loft Music");

    }

    public void pauseButtonClicked()
    {
        if(clip==null)
        {
            pauseImage.setVisible(false);
            resumeImage.setVisible(true);
        }
        else
        {
            clip.stop();
            SongNameArea.setText(" ");
            pauseImage.setVisible(false);
            resumeImage.setVisible(true);

        }

    }
    public void skipButtonClicked()
    {
        int number = myMusicPlayer.getListOfNotLockedMusics().size();
        if(myMusicPlayer.getListOfNotLockedMusics().get(number-1).getName().equals(myMusicPlayer.getCurrentMusic().getName()))
        {
            myMusicPlayer.setCurrentMusic("Library Sound");
        }
        else
        {
            int index = myMusicPlayer.getCurrentMusic().getId() + 1;
            Music temp = myMusicPlayer.getListOfNotLockedMusics().get(index);
            myMusicPlayer.setCurrentMusic(temp);
        }
        SongNameArea.setText(myMusicPlayer.getCurrentMusic().getName());

    }

    public void previousButtonClicked()
    {

        int number = myMusicPlayer.getListOfNotLockedMusics().size();
        if("Library Sound".equals(myMusicPlayer.getCurrentMusic().getName()))
        {
            myMusicPlayer.setCurrentMusic(myMusicPlayer.getListOfNotLockedMusics().get(number-1));
        }
        else
        {
            int index = myMusicPlayer.getCurrentMusic().getId() - 1;
            Music temp = myMusicPlayer.getListOfNotLockedMusics().get(index);
            myMusicPlayer.setCurrentMusic(temp);
        }
        SongNameArea.setText(myMusicPlayer.getCurrentMusic().getName());

    }
    public void showAlert(){
        if(alarm.notify){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alarm");
            alert.setHeaderText(null);
            alert.setContentText("Time is up");
            alert.show();
        }

    }
    public void playAudio(){
        AudioClip aclip = new AudioClip(getClass().getResource("/com/example/myrooms/Sounds/alarmsound.wav").toString());
        aclip.play();


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
        if(isAdmin){
            BoardPane.setVisible(true);
        }

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
            printPostIts();

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
    public void goToComputer(){
        closeEveryPane();
        ComputerPane.setVisible(true);
        ComputerApplications.setVisible(true);

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

        int count = 1;
        for(int i =0; i< length; i++)
        {
            if(myBoard.getPostItArrayList().get(i) != null)
            {
                totalPostitsCharts.setText(count + "");
                count++;
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
    public void purchase(MouseEvent e){
        if(e.getSource() == notebook4 && notebook4RB.isDisable() && totalCoin >= 15){
            notebook4RB.setDisable(false);
            shop.setUnlock("Notebook4",true);
            totalCoin -= 15;
        }
        else if(e.getSource() == notebook3 && notebook3RB.isDisable() && totalCoin >= 10){
            notebook3RB.setDisable(false);
            shop.setUnlock("Notebook3",true);
            totalCoin -= 10;
        }
        else if(e.getSource() == notebook2 && notebook2RB.isDisable() && totalCoin >= 5 ){
            notebook2RB.setDisable(false);
            shop.setUnlock("Notebook2",true);
            totalCoin -= 5;
        }
        else if(e.getSource() == clock3 && clock3RB.isDisable() && totalCoin >= 10 ){
            clock3RB.setDisable(false);
            shop.setUnlock("Clock3",true);
            totalCoin -= 10;
        }
        else if(e.getSource() == clock2png && clock2RB.isDisable() && totalCoin >= 5 ){
            clock2RB.setDisable(false);
            shop.setUnlock("Clock2",true);
            totalCoin -= 5;
        }
        else if(e.getSource() == alarm2png && alarm2RB.isDisable() && totalCoin >= 5 ){
            alarm2RB.setDisable(false);
            shop.setUnlock("Alarm2",true);
            totalCoin -= 5;
        }
        else if(e.getSource() == alarm4 && alarm4RB.isDisable() && totalCoin >= 15){
            alarm4RB.setDisable(false);
            shop.setUnlock("Alarm4",true);
            totalCoin -= 15;
        }
        else if(e.getSource() == alarm3 && alarm3RB.isDisable() && totalCoin >= 10 ){
            alarm3RB.setDisable(false);
            shop.setUnlock("Alarm3",true);
            totalCoin -= 10;
        }
        else if(e.getSource() == board2 && board2RB.isDisable() && totalCoin >= 5 ){
            board2RB.setDisable(false);
            shop.setUnlock("Board2",true);
            totalCoin -= 5;
        }
        else if(e.getSource() == calendar4 && calendar4RB.isDisable() && totalCoin >= 15 ){
            calendar4RB.setDisable(false);
            shop.setUnlock("Calendar4",true);
            totalCoin -= 15;
        }
        else if(e.getSource() == calendar3 && calendar3RB.isDisable() && totalCoin >= 10 ){
            calendar3RB.setDisable(false);
            shop.setUnlock("Calendar3",true);
            totalCoin -= 10;
        }
        else if(e.getSource() == calender2 && calender2RB.isDisable() && totalCoin >= 5 ){
            calender2RB.setDisable(false);
            shop.setUnlock("Calendar2",true);
            totalCoin -= 5;
        }
        else if(e.getSource() == bluepostit && bluePostItCB.isDisable() && totalCoin >= 1 ){
            bluePostItCB.setDisable(false);
            shop.setUnlock("Blue",true);
            blueButton.setDisable(false);
            totalCoin -= 1;
        }
        else if(e.getSource() == greenpostit && greenPostItCB.isDisable() && totalCoin >= 3 ){
            greenPostItCB.setDisable(false);
            shop.setUnlock("Green",true);
            GreenButton.setDisable(false);
            totalCoin -= 3;
        }
        else if(e.getSource() == GFImage && GodfatherThemeCB.isDisable() && totalCoin >= 20 ){
            GodfatherThemeCB.setDisable(false);
            shop.setUnlock("GodFather",true);
            Music temp = myMusicPlayer.getListOfAllMusics().get(4);
            myMusicPlayer.unlockMusic(temp);
            fifthSoundsButton.setVisible(true);
            godFatherText.setVisible(false);


            totalCoin -= 20;
        }
        else if(e.getSource() == LoftImage && LoftMusicCB.isDisable() && totalCoin >= 5 ){
            LoftMusicCB.setDisable(false);
            shop.setUnlock("Loft",true);
            Music temp = myMusicPlayer.getListOfAllMusics().get(6);
            myMusicPlayer.unlockMusic(temp);
            seventhSoundsButton.setVisible(true);
            loftMusicText.setVisible(false);
            totalCoin -= 5;
        }
        else if(e.getSource() == CelloImage && CelloAndPianoCB.isDisable() && totalCoin >= 10 ){
            CelloAndPianoCB.setDisable(false);
            shop.setUnlock("Cello",true);
            Music temp = myMusicPlayer.getListOfAllMusics().get(5);
            myMusicPlayer.unlockMusic(temp);
            sixthSoundsButton.setVisible(true);
            celloAndPianoText.setVisible(false);
            totalCoin -= 10;
        }
        setCoin(totalCoin);
    }
    public void selectPostitColorsShop(ActionEvent e){

        if(e.getSource() == greenPostItCB){
            if(greenPostItCB.isSelected()){
                GreenButton.setDisable(false);
            }
            else
                GreenButton.setDisable(true);
        }
        else if(e.getSource() == bluePostItCB){
            if(bluePostItCB.isSelected()){
                blueButton.setDisable(false);
            }
            else
                blueButton.setDisable(true);
        }
        else if(e.getSource() == yellowPostItCB){
            if(yellowPostItCB.isSelected()){
                yellowButton.setDisable(false);
            }
            else
                yellowButton.setDisable(true);
        }
        else if(e.getSource() == purplePostItCB){
            if(purplePostItCB.isSelected()){
                purpleButton.setDisable(false);
            }
            else
                purpleButton.setDisable(true);
        }
    }
    public void plantScene() {

        if(isAdmin){
            PlantPane.setVisible(!PlantPane.isVisible());
        }

    }
    public  void  returnShopFromPostIts(){
        buyPostItPane.setVisible(!buyPostItPane.isVisible());
        ShopPane.setVisible(!ShopPane.isVisible());
    }
    public void returnShopFromSounds(){
        BuySoundPane.setVisible(!BuySoundPane.isVisible());
        ShopPane.setVisible(!ShopPane.isVisible());
    }

    public void selectItem(ActionEvent e){
        ToggleGroup clockgroup = new ToggleGroup();
        ToggleGroup alarmgroup = new ToggleGroup();

        ToggleGroup boardgroup = new ToggleGroup();
        ToggleGroup calendergroup = new ToggleGroup();
        ToggleGroup notebookgroup = new ToggleGroup();

        standartAlarmRB.setToggleGroup(alarmgroup);
        alarm2RB.setToggleGroup(alarmgroup);
        alarm3RB.setToggleGroup(alarmgroup);
        alarm4RB.setToggleGroup(alarmgroup);

        standartClockRB.setToggleGroup(clockgroup);
        clock2RB.setToggleGroup(clockgroup);
        clock3RB.setToggleGroup(clockgroup);

        standartBoardRB.setToggleGroup(boardgroup);
        board2RB.setToggleGroup(boardgroup);

        standartNoteBookRB.setToggleGroup(notebookgroup);
        notebook2RB.setToggleGroup(notebookgroup);
        notebook3RB.setToggleGroup(notebookgroup);
        notebook4RB.setToggleGroup(notebookgroup);

        standartCalenderRB.setToggleGroup(calendergroup);
        calender2RB.setToggleGroup(calendergroup);
        calendar3RB.setToggleGroup(calendergroup);
        calendar4RB.setToggleGroup(calendergroup);


        if(e.getSource() == standartAlarmRB && !standartAlarmRB.isDisable()){
            alarm.imagePath = "CsProject-BackGrounds/Alarm.png";
            setAlarmImage(alarm.imagePath);
        }
        else if(e.getSource() == alarm2RB && !alarm2RB.isDisable()){
            alarm.imagePath = "CsProject-BackGrounds/Alarm2.png";
            setAlarmImage(alarm.imagePath);
        }
        else if(e.getSource() == alarm3RB && !alarm3RB.isDisable()){
            alarm.imagePath = "CsProject-BackGrounds/Alarm3.png";
            setAlarmImage(alarm.imagePath);
        }
        else if(e.getSource() == alarm4RB && !alarm4RB.isDisable()){
            alarm.imagePath = "CsProject-BackGrounds/Alarm4.png";
            setAlarmImage(alarm.imagePath);
        }

        else if(e.getSource() == standartClockRB ){
            clock1.imagePath = "CsProject-BackGrounds/Clock.png";
            setClockImage(clock1.imagePath);

        }
        else if(e.getSource() == clock2RB ){
            clock1.imagePath = "CsProject-BackGrounds/Clock2.png";
            setClockImage(clock1.imagePath);
        }
        else if(e.getSource() == clock3RB ){
            clock1.imagePath = "CsProject-BackGrounds/Clock3.png";
            setClockImage(clock1.imagePath);
        }
         else if(e.getSource() == standartCalenderRB && !standartCalenderRB.isDisable()){
             calendars.imagePath ="CsProject-BackGrounds/Calendar.png";
             setCalendarImage(calendars.imagePath);
         }
        else if(e.getSource() == calender2RB && !calender2RB.isDisable()){
             calendars.imagePath = "CsProject-BackGrounds/Calendar2.png";
            setCalendarImage(calendars.imagePath);
        }
         else if(e.getSource() == calendar3RB && !calendar3RB.isDisable()){
             calendars.imagePath = "CsProject-BackGrounds/Calendar3.png";
             setCalendarImage(calendars.imagePath);
         }
         else if(e.getSource() == calendar4RB && !calendar4RB.isDisable()){
             calendars.imagePath = "CsProject-BackGrounds/Calendar4.png";
             setCalendarImage(calendars.imagePath);
         }
        else if(e.getSource() == standartBoardRB && !standartBoardRB.isDisable()){
            myBoard.imagePath = "CsProject-BackGrounds/HalfBoard.png";
            setBoardImage(myBoard.imagePath);
        }
        else if(e.getSource() == board2RB && !board2RB.isDisable()){
            myBoard.imagePath = "CsProject-BackGrounds/Board2.png";
            setBoardImage(myBoard.imagePath);
        }
        else if(e.getSource() == standartNoteBookRB && !standartNoteBookRB.isDisable()){
            notebooks.imagePath = "CsProject-BackGrounds/Notebook.png";
            setNotebook(notebooks.imagePath);
        }
         else if(e.getSource() == notebook2RB && !notebook2RB.isDisable()){
             notebooks.imagePath = "CsProject-BackGrounds/NoteBook2.png";
             setNotebook(notebooks.imagePath);
         }
         else if(e.getSource() == notebook3RB && !notebook3RB.isDisable()){
             notebooks.imagePath = "CsProject-BackGrounds/NoteBook3.png";
             setNotebook(notebooks.imagePath);
         }
         else if(e.getSource() == notebook4RB && !notebook4RB.isDisable()){
             notebooks.imagePath = "CsProject-BackGrounds/NoteBook4.png";
             setNotebook(notebooks.imagePath);
         }
    }
    public void buyPostItAction(){
        buyPostItPane.setVisible(!buyPostItPane.isVisible());
        ShopPane.setVisible(!ShopPane.isVisible());
    }
    public void buySoundAction(){
        BuySoundPane.setVisible(!BuySoundPane.isVisible());
        ShopPane.setVisible(!ShopPane.isVisible());
    }
    public void bookCaseScene() {

        if(isAdmin){
            bookPane.setVisible(!bookPane.isVisible());
        }

    }
    public void addImage(){

        String imagePath ="CsProject-BackGrounds/Book.png";
        if(!bookName.equals("")){

            Book book = new Book(bookTextField.getText(),  imagePath);
            int count = bookcase.getCount();
            bookcase.books.add(book);
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
        int counts = 1;

        for(Book b : bookcase.books){

            if(b != null){

                totalBooksCharts.setText(counts + "");
                counts++;
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
    public void growPlant(){

        plant.setLevelOfPlant(plant.getLevelOfPlant() + 1);
        plantLevelCharts.setText(plant.getLevelOfPlant() + "");
        if(plant.getLevelOfPlant() == 1){
            plant.imagePath = "CsProject-BackGrounds/plantlevel2.png";

        }
        else if (plant.getLevelOfPlant() == 2) {
            plant.imagePath = "CsProject-BackGrounds/plantlevel3.png";
        }
        else if (plant.getLevelOfPlant() >= 3) {
            plant.imagePath = "CsProject-BackGrounds/plantlevel4.png";
        }
        setPlantImage(plant.imagePath);
    }

    public void setPlantImage(String image){
        PlantImage.setImage(new Image(image));
        BigPlantImage.setImage(new Image(image));

    }
    public void setWaterCanImage(String image){
        WaterCanImage.setImage(new Image(image));
    }
    public void waterplant(){

        if(plant.watercan.getWaterLevel() == 1){
            plant.watercan.imagePath = "CsProject-BackGrounds/watercanempty.png";
            setWaterCanImage(plant.watercan.imagePath);
        }
        else if (plant.watercan.getWaterLevel() == 2) {
            plant.watercan.imagePath = "CsProject-BackGrounds/watercanhalf.png";
            setWaterCanImage(plant.watercan.imagePath);
        }
        else if (plant.watercan.getWaterLevel() == 3) {
            plant.watercan.imagePath = "CsProject-BackGrounds/watercanfull.png";
            setWaterCanImage(plant.watercan.imagePath);
        }

    }
    public void waterPlantButton(){
        if(plant.watercan.getWaterLevel() >= plant.watercan.getWatercanLevel()){

            growPlant();
            plant.watercan.setWaterLevel(1);

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
            myBoard = user.room.board;
            totalCoin = user.totalCoin;
            totalTimeSpent = user.totalHours;
            plant = user.room.plant;
            notebooks = user.room.notebooks;
            calendars = user.room.calendars;
            shop = user.room.shop;
            settings = user.room.settings;
            myMusicPlayer = user.room.musicPlayer;

        } catch (Exception e) {
            e.printStackTrace();
        }
        setAlarmImage(alarm.imagePath);
        setClockImage(clock1.imagePath);
        setCoin(totalCoin);
        setCalendarImage(calendars.imagePath);
        setTotalTime(totalTimeSpent);
        setBoardImage(myBoard.imagePath);
        setNotebook(notebooks.imagePath);
        putAndSortBooks();
        printPostIts();
        loadShop();
        settings.notify = alarm.notify;
        
        if(settings.notify){
            settingsNotificationsButton.setText("Off");
        }
        else{
            settingsNotificationsButton.setText("On");
        }
        settingsBrightnessSlider.setValue(settings.brightness);
        settingsVolumeSlider.setValue(settings.volume);

        aclip = new AudioClip(getClass().getResource("/com/example/myrooms/Sounds/librarySounds.mp3").toString());
        totalTimeCharts.setText(user.totalHours + "");

        if(!alarm.isOn){
            alarmCreate.setText("Start Alarm");
        }
        else{
            alarmCreate.setText("Stop Alarm");
        }

        InitializeCalendarNotebook();
        for(PostIt b : myBoard.getPostItArrayList()){
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

        settingsVolumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                volume = settingsVolumeSlider.getValue();
                mediaPlayer.setVolume(volume / 100);
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

        //allPane.setEffect(lightingEffect);

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
            totalTimeSpent++;
            user.totalCoin = totalCoin;
            user.totalHours = totalTimeSpent;
            waterplant();
            UserManager.saveUser(user);

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
