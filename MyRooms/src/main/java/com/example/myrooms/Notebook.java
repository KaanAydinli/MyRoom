package com.example.myrooms;
import com.calendarfunctionality.Calendar;
import com.calendarfunctionality.CalendarFunctionality;
import com.calendarfunctionality.Day;
import com.calendarfunctionality.Task;
import com.habittracker.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Notebook extends Pane
{
    // Elements of habit tracker view

    @FXML
    private Node progressTypeView,
            addFrequencyTypeView,
            habitTypeView,
            habitView,
            habitDetailedView,
            habitInfoView,
            dailyPlannerPane,
            activeHabitsPane;
    @FXML
    private TextField progressTypeNameTF,
            progressTF,
            progressCoefTF,
            frequenceNameTF,
            habitTypeNameTF,
            habitTypeDescTF,
            habitNameTF,
            habitDescTF,
            habitNameTFDetailed,
            habitDescTFDetailed;


    @FXML
    private CheckBox weekCB1,weekCB2,weekCB3,weekCB4,weekCB5,weekCB6,weekCB7,
            monthCB1,monthCB2,monthCB3,monthCB4,monthCB5,monthCB6,monthCB7,
            monthCB8,monthCB9,monthCB10,monthCB11,monthCB12,
            cbEveryDay, cbEveryMonth;



    private CheckBox[] cbWeekArr;
    private CheckBox[] cbMonthArr;

    private int[] weekOccurrences = new int[7];
    private int[] monthlyOccurrences = new int[12];

    @FXML
    private ScrollPane progressTypeScrollPane,
            frequenceTypeScrollPane,
            habitTypeScrollPane,
            habitsSmallScrollPane,
            habitInfoScrollPane,
            allHabitsScrollPane,
            activeHabitsScrollPane,
            dailyViewScrollPane,
            dailyTaskScrollPane;


    @FXML
    private TitledPane progressTypeTitledPane,
            frequenceTypeTitledPane;

    @FXML
    private ProgressIndicator habitDetailedProgressIndicator;

    @FXML
    private Button progressTypeDeleteB,
            progressTypeAddB,
            frequenceTypeDeleteB,
            frequenceTypeAddB,
            habitTypeAddB,
            habitTypeDeleteB,
            resetProgressButton,
            habitDeleteB,
            habitChangeB,
            habitDetailedInfoB,
            dailyHabitsReplaceB;

    @FXML
    private Label habitInfoLabel;

    @FXML
    private ChoiceBox<String> defaultProgressBox,
            defaultOccurrenceBox,
            hHabitTypeChooser,
            hProgressTypeChooser,
            hFrequenceChooser,
            hProgressTypeChooserDetailed,
            hHabitTypeChooserDetailed,
            hFrequenceChooserDetailed;

    //task stuff
    @FXML
    private ComboBox<String>    calendarComboBox,
            taskNotificationComboBox;
    @FXML
    private CheckBox taskDoneCB,
            taskAllDayCB;
    @FXML
    private DatePicker taskDatePicker,
            currentDatePicker;

    @FXML
    private Node taskView;

    @FXML
    private TextField
            taskNameTF,
            taskStartHTF,
            taskEndHTF,
            taskStartMTF,
            taskEndMTF;
    @FXML
    private Button
            taskDeleteB,
            addTaskB;


    ArrayList<HabitButton> habitButtons = new ArrayList<>();
    ArrayList<HabitButton> habitButtonsSecond = new ArrayList<>();
    ArrayList<HabitButton> activeHabitButtons = new ArrayList<>();

    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);


    private Node innerPane; //
    User user;
    HabitTracker habitTracker;

    Day today;
    CalendarFunctionality calendarFunctionality;

    ArrayList<Task> todayTasks;

    DailyPlanner dailyPlanner;

    public Notebook(Pane Window, User user, CalendarFunctionality calendarFunctionality)
    {
        this.setPrefSize(900,580);
        this.setStyle("-fx-background-color: beige;");

        this.setLayoutX(100);
        this.setLayoutY(15);
        this.user = user;

        habitTracker = new HabitTracker(user.ID);
        InitializeHabitButtons(habitButtons);
        InitializeHabitButtons(habitButtonsSecond);
        Window.getChildren().add(this);
        this.setVisible(false);
        loadFXML("notebook_dailyTask");
        this.setVisible(false);
        this.today = calendarFunctionality.getSpecificDay(LocalDate.now());
        dailyPlanner = new DailyPlanner();
        this.calendarFunctionality = calendarFunctionality;
    }
    public void notebookOpen()
    {
        this.setVisible(true);
        goToDailyTaskView(new ActionEvent());
        this.toFront();
    }
    public void notebookClose()
    {
        this.setVisible(false);
    }
    //loading the wanted
    private void loadFXML(String name)
    {
        String location = String.format("notebooks/%s.fxml",name);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(location));

            loader.setController(this);
            innerPane = loader.load();
            innerPane.setStyle("-fx-background-color: transparent;");
            this.getChildren().add(innerPane);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML", e);
        }
    }
    public void goToHabitTrackerView(ActionEvent e)
    {
        this.getChildren().remove(innerPane);
        loadFXML("notebook_habitTracker");
        habitTypeScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        habitTypeScrollPane.setFitToWidth(true);;
        cbWeekArr = new CheckBox[]{weekCB1,weekCB2,weekCB3,weekCB4,weekCB5,weekCB6,weekCB7};
        cbMonthArr = new CheckBox[]{monthCB1,monthCB2,monthCB3,monthCB4,monthCB5,monthCB6
                ,monthCB7,monthCB8,monthCB9,monthCB10,monthCB11,monthCB12};
        addHabitProgressButtons();
        addFrequenceTypeButtons();
        addHabitTypeButtons();
        setSmallHabitPane();
        setUpAllHabitScrollFrame();

        progressTypeScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        progressTypeScrollPane.setFitToWidth(true);;

        frequenceTypeScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        frequenceTypeScrollPane.setFitToWidth(true);

        habitsSmallScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        habitsSmallScrollPane.setFitToWidth(true);

    }
    public void goToDailyTaskView(ActionEvent e)
    {
        this.getChildren().remove(innerPane);
        loadFXML("notebook_dailyTask");
        dailyPlanner.setDailyViewView();
        dailyPlanner.setGeneralView();
        addTasksToPane();
    }
    public void goToDailyHabitsView(ActionEvent e)
    {
        this.getChildren().remove(innerPane);
        goToDailyTaskView(e);
    }
    //Task Actions
    public void taskClicked(ActionEvent event)
    {
        String notificationM;
        int notificationMin;
        Button source;
        Task task;
        Calendar calendar;
        source = (Button) event.getSource();
        task = (Task) source.getUserData();

        taskViewOpen(event);
        taskNameTF.setText(task.getName());
        taskDatePicker.setValue(task.getDate());
        taskStartHTF.setText(String.valueOf(task.getStartH()));
        taskStartMTF.setText(String.valueOf(task.getStartM()));
        taskEndHTF.setText(String.valueOf(task.getEndH()));
        taskEndMTF.setText(String.valueOf(task.getEndM()));
        calendar = calendarFunctionality.getTaskCalendar(task);
        calendarComboBox.setValue(calendar.toString());

        calendarComboBox.setDisable(true);
        taskNotificationComboBox.setDisable(true);
        //task
        taskDeleteB.setUserData(task);
        taskDeleteB.setVisible(true);

        taskDatePicker.setValue(this.today.getDate());
        taskDatePicker.setDisable(true);

        addTaskB.setText("Change");
        addTaskB.setOnAction(Notebook.this::changeTask);
        addTaskB.setUserData(task);

        notificationMin = task.getNotificationM();
        if(notificationMin == 42)
        {
            notificationM = "None";
        }
        else
        {
            notificationM = String.format("%d minutes bfr",task.getNotificationM());
        }

        taskNotificationComboBox.setValue(notificationM);

        taskDoneCB.setSelected(task.getIsDone());
        taskAllDayCB.setSelected(task.isAllDay());
    }
    public void changeTask(ActionEvent event)
    {
        Button source = (Button) event.getSource();
        Task task = (Task)  source.getUserData();

        String name,
                description,
                calendarCB,
                notificationStr;
        int startH,
                startM,
                endH,
                endM,
                notificationM,
                calendarNo;
        boolean allDay,
                isDone;
        LocalDate date;

        alert.setTitle("Error in Changing Task");
        try{
            name = taskNameTF.getText();
            startH = Integer.parseInt(taskStartHTF.getText());
            startM = Integer.parseInt(taskStartMTF.getText());
            endH = Integer.parseInt(taskEndHTF.getText());
            endM = Integer.parseInt(taskEndMTF.getText());
            notificationStr = taskNotificationComboBox.getValue();
            date = taskDatePicker.getValue();
            allDay = taskAllDayCB.isSelected();
            isDone = taskDoneCB.isSelected();
            calendarCB = calendarComboBox.getValue();

        } catch (Exception x) {

            alert.setContentText("Error in changing task");
            alert.showAndWait();
            return;
        }

        //check whether start date end date;
        if((name.isEmpty()) || (date == null) ||(calendarCB.isEmpty()))
        {
            alert.setContentText("Please enter everything");
            alert.showAndWait();
            return;
        }

        if(notificationStr.equals("None"))
        {
            //meaning of life
            notificationM = 42;
        }
        else
        {
            notificationM = Integer.parseInt(notificationStr.substring(0,2));
        }


        calendarNo = Integer.parseInt(calendarCB.substring(0,1));
        Calendar calendar = calendarFunctionality.getSpecificCalendar(calendarNo);

        calendarFunctionality.changeTask(name,date,calendar,startH, startM,endH, endM,allDay,isDone, notificationM,task);
        taskViewClose();
        addTasksToPane();
        dailyPlanner.setGeneralView();
        dailyPlanner.setDailyViewView();
    }
    public void taskViewOpen(ActionEvent event)
    {
        taskNotificationComboBox.setDisable(false);
        calendarComboBox.setDisable(false);
        taskDeleteB.setVisible(false);
        taskDatePicker.setDisable(false);
        addTaskB.setText("Add");
        addTaskB.setOnAction(this::addTaskClicked);
        taskView.toFront();
        taskView.setVisible(true);
        System.out.println("taskViewOpen");
    }
    public void taskViewClose()
    {
        taskView.setVisible(false);
        taskNameTF.clear();
        taskDoneCB.setSelected(false);
        taskAllDayCB.setSelected(false);

        taskDatePicker.setValue(null);
        taskStartHTF.clear();
        taskEndHTF.clear();
        taskStartMTF.clear();
        taskEndMTF.clear();
        taskNotificationComboBox.setValue(null);
        calendarComboBox.setValue(null);
    }
    public void addTaskClicked(ActionEvent event)
    {
        String name,
                description,
                calendarCB,
                notificationStr;
        int startH,
                startM,
                endH,
                endM,
                notificationM,
                calendarNo;
        boolean allDay,
                dailyTask;
        LocalDate date;


        alert.setTitle("Error in adding task");
        try{
            name = taskNameTF.getText();
            startH = Integer.parseInt(taskStartHTF.getText());
            startM = Integer.parseInt(taskStartMTF.getText());
            endH = Integer.parseInt(taskEndHTF.getText());
            endM = Integer.parseInt(taskEndMTF.getText());
            notificationStr = taskNotificationComboBox.getValue();
            date = taskDatePicker.getValue();
            allDay = taskAllDayCB.isSelected();
            calendarCB = calendarComboBox.getValue();

        } catch (Exception x) {

            alert.setContentText("Error in adding task");
            alert.showAndWait();
            return;
        }

        //check whether start date end date;
        if((name.isEmpty()) || (date == null) ||(calendarCB.isEmpty()))
        {
            alert.setContentText("Please enter everything");
            alert.showAndWait();
            return;
        }

        if(notificationStr.equals("None"))
        {
            //meaning of life
            notificationM = 42;
        }
        else
        {
            notificationM = Integer.parseInt(notificationStr.substring(0,2));
        }


        calendarNo = Integer.parseInt(calendarCB.substring(0,1));
        Calendar calendar = calendarFunctionality.getSpecificCalendar(calendarNo);
        calendarFunctionality.addTask(name,date,calendar,startH, startM,endH, endM,allDay, notificationM);
        taskViewClose();
        addTasksToPane();
        dailyPlanner.setGeneralView();
    }
    public void deleteTask(ActionEvent event)
    {
        Task task;
        Button source;
        source = (Button) event.getSource();
        task = (Task) source.getUserData();

        confirmation.setHeaderText("Are you sure you want to delete this task?");
        confirmation.setContentText("This action cannot be undone.");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                calendarFunctionality.deleteTask(task);
            }
        });
        taskViewClose();
        addTasksToPane();
        dailyPlanner.setGeneralView();
        dailyPlanner.setDailyViewView();


    }
    public void setNotificationComboBox()
    {
        taskNotificationComboBox.getItems().clear();
        taskNotificationComboBox.getItems().add("None");
        taskNotificationComboBox.getItems().add("10 minute bfr");
        taskNotificationComboBox.getItems().add("30 minute bfr");
        taskNotificationComboBox.getItems().add("60 minute bfr");
        taskNotificationComboBox.setValue("None");
    }
    public void setCalendarComboBox()
    {
        calendarComboBox.getItems().clear();
        ArrayList<Calendar> list = calendarFunctionality.getCalendars();
        for(Calendar element: list)
        {
            calendarComboBox.getItems().add(element.toString());
        }
    }

    public void dailyPlannerShow(ActionEvent e)
    {
        activeHabitsPane.setVisible(false);
        dailyViewScrollPane.setVisible(true);
        dailyPlannerPane.setVisible(true);
    }

    //tasks and daily habits view
    public void activeHabitsShow(ActionEvent e)
    {
        InitializeActiveHabitButtons();
        dailyPlannerPane.setVisible(false);
        activeHabitsPane.setVisible(true);
        setActiveHabitsScrollPane();
    }

    public void setActiveHabitsScrollPane()
    {
        int size,
                row,
                column;
        GridPane gridPane = new GridPane();
        HabitButton button;

        gridPane.setHgap(20);
        gridPane.setVgap(20);

        InitializeActiveHabitButtons();
        size = activeHabitButtons.size();
        row = 0;
        column = 0;
        for(int i = 0; i < size; i++)
        {
            button = activeHabitButtons.get(i);
            GridPane.setHgrow(button, Priority.ALWAYS);
            button.setMaxWidth(Double.MAX_VALUE);
            if(column>2)
            {
                column = 0;
                row +=1;
            }
            gridPane.add(button, column, row);
            column ++;
        }
        activeHabitsScrollPane.setContent(gridPane);
        activeHabitsScrollPane.setFitToWidth(true);

    }

    public void InitializeActiveHabitButtons()
    {
        ArrayList<Habit> habits = habitTracker.getHabits();
        activeHabitButtons.clear();
        int size = habits.size();
        Habit habit;
        for(int i = 0; i < size; i++)
        {
            habit = habits.get(i);
            if(habit.getIsActive())
            {
                HabitButton habitButton = new HabitButton(habit);
                activeHabitButtons.add(habitButton);

            }
        }
    }

    public void addTasksToPane()
    {
        int size,
                row,
                column;
        Task task;
        ArrayList<Task> tasks = today.getTasks();
        GridPane gridPane = new GridPane();
        size = tasks.size();
        gridPane.setHgap(40);
        gridPane.setVgap(20);
        gridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        row = 0;
        column = 0;
        for(int i = 0; i < size; i++)
        {
            if(column>2)
            {
                column = 0;
                row += 1;
            }

            task = tasks.get(i);
            Button button = new Button(task.getName());
            GridPane.setHgrow(button, Priority.ALWAYS);
            button.setMaxWidth(Double.MAX_VALUE);
            //TODO add show function
            button.setUserData(task);
            button.setOnAction(this::taskClicked);
            gridPane.add(button, column, row);
            button.setMinWidth(125);
            button.setMaxWidth(125);
            button.setMinHeight(50);
            column ++;
        }
        dailyTaskScrollPane.setContent(gridPane);
    }

    public class DailyPlanner extends Pane
    {

        Label dateLabel = new Label();
        VBox allDayTasks = new VBox();
        LocalDate date;
        Day day;

        public DailyPlanner()
        {
            this.day = today;
            dateLabel.setText(day.getDate().toString());
            setGeneralView();
            this.date = day.getDate();
        }

        public void setGeneralView()
        {
            int y,
                    hour;
            Label hourLabel;
            Separator separator;
            this.getChildren().clear();
            dateLabel.setMaxHeight(25);
            dateLabel.setMinHeight(25);
            this.getChildren().add(dateLabel);
            separator = new Separator();
            separator.setLayoutY(25);
            separator.setPrefWidth(this.getWidth());
            this.getChildren().add(separator);
            allDayTasks.setMaxHeight(150);
            allDayTasks.setMinHeight(150);
            allDayTasks.setLayoutY(15);
            this.getChildren().add(allDayTasks);

            hour = 5;
            y=175;
            for(int i = 0; i<24;i++)
            {
                if(hour>23)
                {
                    hour = 0;
                }
                separator  = new Separator();
                separator.setLayoutY(y);
                separator.setPrefWidth(this.getWidth());
                hourLabel = new Label(String.valueOf(hour));
                hourLabel.setLayoutY(y);
                hour += 1;
                this.getChildren().add(separator);
                this.getChildren().add(hourLabel);
                y+= 60;
            }
            addTasks();
        }
        public void addTasks()
        {
            int size,
                    y,
                    startH,
                    startM,
                    endH,
                    endM,
                    buttonSize;
            Task task;
            Button button;
            ArrayList<Task> tasks = day.getTasks();
            size = tasks.size();

            allDayTasks.getChildren().clear();
            for(int i=0; i<size; i++)
            {
                task = tasks.get(i);
                button = new Button(task.toString());
                if(task.isAllDay())
                {
                    allDayTasks.getChildren().add(button);
                }
                else
                {
                    startH = task.getStartH();
                    startM = task.getStartM();
                    endH = task.getEndH();
                    endM = task.getEndM();
                    y = calculatePosition(startH,startM);
                    buttonSize = calculatePosition(endH,endM) - y;


                    this.getChildren().add(button);
                    button.setLayoutY(y);
                    button.setLayoutX(20);
                    button.setPrefWidth(this.getWidth());
                    button.setMinWidth(buttonSize);
                    button.setMaxWidth(buttonSize);
                    button.setPrefHeight(buttonSize);
                }
                button.setUserData(task);
                button.setOnAction(Notebook.this::taskClicked);
            }
        }
        public int calculatePosition(int H,int M)
        {
            int result;
            if(H-5<0)
            {
                H= H+5;
            }
            result = 175 + (H-5)*60 + M;
            return result;
        }
        public void setDailyViewView()
        {
            dailyViewScrollPane.setContent(this);
        }
    }


    public void exitDailyTaskHabitPopUps()
    {
        closeHabitDetailedView(null);
        habitInfoView.setVisible(false);
        taskViewClose();

    }



    public class HabitButton extends GridPane
    {
        Button progressButton,
                habitButton;

        ProgressIndicator progressIndicator;

        Habit habit;

        double progressPercentage;

        int wantedProgress;

        private boolean activity;

        public HabitButton(Habit habit)
        {

            this.habit = habit;
            activity = habit.getIsActive();
            progressButton = new Button();
            habitButton = new Button();
            habitButton.setText(habit.getName());

            if(activity)
            {
                progressIndicator = new ProgressIndicator(0.0);
                progressButton.setGraphic(progressIndicator);
                wantedProgress = habit.getWantedProgress();
                setProgressPercentage();
                progressIndicator.setMouseTransparent(true);
            }
            else
            {
                progressButton.setText("X");
                progressButton.setDisable(true);
            }
            habitButton.setOnAction(this::habitClicked);

            progressButton.setOnAction(this::progressButtonClicked);

            progressButton.setMaxWidth(50);
            progressButton.setMinWidth(50);
            this.add(progressButton, 0, 0);
            this.add(habitButton, 1, 0);

            habitButton.setMaxWidth(Double.MAX_VALUE);
            habitButton.setMaxHeight(Double.MAX_VALUE);
            setHgrow(habitButton, Priority.ALWAYS);
        }
        public void setProgressPercentage()
        {
            int currentProgress,
                    percentage;

            currentProgress = habit.getCurrentProgress();

            progressPercentage = (double) currentProgress /this.wantedProgress;
            this.progressIndicator.setProgress(progressPercentage);
        }

        public void progressButtonClicked(ActionEvent e)
        {
            habit.makeProgress();
            setProgressPercentage();
            if(allHabitsScrollPane == null || habitsSmallScrollPane == null)
            {
                return;
            }
            setSmallHabitPane();
            setUpAllHabitScrollFrame();

        }

        public void habitClicked(ActionEvent actionEvent)
        {
            habitDetailedView.setVisible(true);
            habitDetailedView.toFront();
            habitNameTFDetailed.setText(habit.getName());
            habitDescTFDetailed.setText(habit.getDescription());
            hHabitTypeChooserDetailed.setValue(habit.getHabitType().toString());
            hFrequenceChooserDetailed.setValue(habit.getFrequence().toString());
            hProgressTypeChooserDetailed.setValue(habit.getHabitProgressType().toString());
            if(activity)
            {
                habitDetailedProgressIndicator.setVisible(true);
            }
            else
            {
                habitDetailedProgressIndicator.setVisible(false);
            }
            habitDetailedProgressIndicator.setProgress(progressPercentage);

            habitDeleteB.setUserData(habit);
            habitChangeB.setUserData(habit);
            resetProgressButton.setUserData(habit);
            habitDetailedInfoB.setUserData(habit);

        }
    }


    public class HabitLookFrame extends VBox
    {
        Habit habit;
        LocalDate date;
        Label dateLabel;
        CheckBox habitCheckBox;
        int index,
                doneDateVal;


        public HabitLookFrame(Habit habit,int index,int doneDateVal,LocalDate date)
        {
            this.habit = habit;
            this.index = index;
            this.date = date;
            this.doneDateVal = doneDateVal;
            this.setAlignment(Pos.CENTER);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yy");


            habitCheckBox = new CheckBox();
            habitCheckBox.setOnAction(this::checkBoxClicked);

            habitCheckBox.setStyle("-fx-font-size: 18px;"); // Increase font size
            dateLabel = new Label();
            dateLabel.setText(date.format(formatter));
            if(doneDateVal == 1)
            {
                habitCheckBox.setSelected(true);
            } else if (doneDateVal == 3)
            {
                habitCheckBox.setDisable(true);
            }
            this.getChildren().add(dateLabel);
            this.getChildren().add(habitCheckBox);
        }

        public void checkBoxClicked(ActionEvent e)
        {
            if(habitCheckBox.isSelected())
            {
                habit.doDate(date);
                setSmallHabitPane();
                setUpAllHabitScrollFrame();
            }
            else
            {
                habit.undoDate(date);
                setSmallHabitPane();
                setUpAllHabitScrollFrame();
            }

        }


    }


    public void setUpHabitInfoView(Habit habit)
    {
        int size,
                row,
                column,
                doneDateVal;
        LocalDate date;
        ArrayList<LocalDate> activeDates = habit.getActiveDates();
        ArrayList<Integer> doneDates = habit.getDoneDates();
        GridPane gridPane = new GridPane();
        HabitLookFrame habitLook;

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        size = activeDates.size();
        row = 0;
        column = 0;

        for(int i = 0; i < size; i++)
        {
            doneDateVal = doneDates.get(i);
            date = activeDates.get(i);
            habitLook = new HabitLookFrame(habit,i,doneDateVal,date);
            if(column>5)
            {
                column = 0;
                row +=1;
            }
            gridPane.add(habitLook, column, row);
            column ++;
        }
        habitInfoScrollPane.setContent(gridPane);
        habitInfoScrollPane.setFitToWidth(true);
    }

    public void habitInfoView(ActionEvent event)
    {
        Button source;
        Habit habit;

        source = (Button) event.getSource();
        habit = (Habit) source.getUserData();
        habitInfoView.setVisible(true);
        habitInfoView.toFront();
        habitInfoLabel.setText(habit.getName());
        setUpHabitInfoView(habit);
    }
    private void closeHabitInfoView()
    {
        habitInfoView.setVisible(false);
    }

    public void resetHabitProgress(ActionEvent e)
    {
        Button source;
        Habit habit;
        LocalDate today;

        source = (Button) e.getSource();
        habit = (Habit) source.getUserData();

        today = LocalDate.now();
        habit.undoDate(today);
        habitDetailedProgressIndicator.setProgress(0.0);
        if(habitsSmallScrollPane == null || allHabitsScrollPane == null)
        {
            return;
        }
        setSmallHabitPane();
        setUpAllHabitScrollFrame();

    }
    public void InitializeHabitButtons(ArrayList<HabitButton> buttonList)
    {
        ArrayList<Habit> habits = habitTracker.getHabits();
        buttonList.clear();
        int size = habits.size();
        Habit habit;
        for(int i = 0; i < size; i++)
        {
            habit = habits.get(i);
            HabitButton habitButton = new HabitButton(habit);
            buttonList.add(habitButton);
        }
    }


    public void setUpAllHabitScrollFrame()
    {

        int size,
                row,
                column;
        GridPane gridPane = new GridPane();
        HabitButton button;

        gridPane.setHgap(20);
        gridPane.setVgap(20);

        InitializeHabitButtons(habitButtonsSecond);
        size = habitButtonsSecond.size();
        row = 0;
        column = 0;
        for(int i = 0; i < size; i++)
        {
            button = habitButtonsSecond.get(i);
            GridPane.setHgrow(button, Priority.ALWAYS);
            button.setMaxWidth(Double.MAX_VALUE);
            if(column>2)
            {
                column = 0;
                row +=1;
            }
            gridPane.add(button, column, row);
            column ++;
        }
        allHabitsScrollPane.setContent(gridPane);
        allHabitsScrollPane.setFitToWidth(true);
    }

    //Methods of Habit Tracker
    public void openHabitView(ActionEvent e)
    {
        habitView.setVisible(true);
        habitView.toFront();
        System.out.println("habit view");

        setProgressBox(hProgressTypeChooser);
        setOccurrenceBox(hFrequenceChooser);
        setHabitTypeBox(hHabitTypeChooser);
        habitTypeDeleteB.setVisible(false);
        habitTypeAddB.setOnAction(this::addHabitTypeClicked);

    }

    public void closeHabitView()
    {
        habitView.setVisible(false);
        hProgressTypeChooser.getItems().clear();
        hFrequenceChooser.getItems().clear();
        hHabitTypeChooser.getItems().clear();
        habitNameTF.clear();
        habitDescTF.clear();
    }

    //about progress types
    public void addProgressTypeClicked(ActionEvent e)
    {
        progressTypeView.toFront();
        progressTypeView.setVisible(true);
        progressTypeDeleteB.setVisible(false);
        progressTypeAddB.setText("Add");
        progressTypeAddB.setOnAction(this::addProgressTypeN);
    }

    public void addProgressTypeN(ActionEvent e)
    {

        String name;
        int progressType,
                progressCoef;

        alert.setTitle("Error in adding progress type");
        alert.setHeaderText("Error Details");

        try{
            name = progressTypeNameTF.getText();
            progressType = Integer.parseInt(progressTF.getText());
            progressCoef = Integer.parseInt(progressCoefTF.getText());

        } catch (Exception x) {

            alert.setContentText("Please enter integers");
            alert.showAndWait();
            return;
        }
        if((progressType == 0)||(progressCoef == 0)||(name.isEmpty()))
        {
            alert.setContentText("Can not be blank");
            alert.showAndWait();
            return;
        }
        habitTracker.addHabitProgress(name,progressType,progressCoef);
        exitProgressTypeView();
        addHabitProgressButtons();
    }

    public void exitProgressTypeView()
    {
        progressTypeNameTF.clear();
        progressTF.clear();
        progressCoefTF.clear();
        progressTypeView.setVisible(false);
    }
    public void habitProgressButtonClicked(ActionEvent event)
    {
        Button source;
        String name;
        HabitProgressType habitProgressType;
        int progress,
                progressCoef;


        source = (Button) event.getSource();
        habitProgressType =(HabitProgressType) source.getUserData();
        name = habitProgressType.getName();
        progress = habitProgressType.getProgress();
        progressCoef = habitProgressType.getProgressCoefficient();

        addProgressTypeClicked(event);
        progressTypeDeleteB.setVisible(true);
        progressTypeNameTF.setText(name);
        progressTF.setText(String.valueOf(progress));
        progressCoefTF.setText(String.valueOf(progressCoef));
        progressTypeAddB.setText("Change");
        progressTypeAddB.setOnAction(this::changeProgressType);
        progressTypeAddB.setUserData(habitProgressType);
        progressTypeDeleteB.setUserData(habitProgressType);
    }
    public void changeProgressType(ActionEvent event)
    {
        String name;
        int progressType,
                progressCoef;
        HabitProgressType habitProgressType;
        Button source;

        alert.setTitle("Error in changing type");
        alert.setHeaderText("Error Details");

        source = (Button) event.getSource();
        habitProgressType = (HabitProgressType) source.getUserData();

        try{
            name = progressTypeNameTF.getText();
            progressType = Integer.parseInt(progressTF.getText());
            progressCoef = Integer.parseInt(progressCoefTF.getText());

        } catch (Exception x) {

            alert.setContentText("Please enter integers");
            alert.showAndWait();
            return;
        }
        if((progressType == 0)||(progressCoef == 0)||(name.isEmpty()))
        {
            alert.setContentText("Can not be blank");
            alert.showAndWait();
            return;
        }
        habitTracker.changeHabitProgressType(name,progressType,progressCoef,habitProgressType);
        exitProgressTypeView();
        addHabitProgressButtons();
    }
    public void addHabitProgressButtons()
    {
        int size;
        HabitProgressType element;

        ArrayList <HabitProgressType> list = habitTracker.getHabitProgressTypes();
        size = list.size();
        System.out.println(size);
        VBox vBox = new VBox();

        for(int i = 0; i<size; i++)
        {
            element = list.get(i);
            Button button = new Button(element.toString());
            button.setUserData(element);
            button.setPrefWidth(190);
            button.setOnAction(this::habitProgressButtonClicked);
            vBox.getChildren().add(button);

        }
        progressTypeScrollPane.setContent(vBox);
    }
    public void deleteHabitProgress(ActionEvent event)
    {
        Button source;
        HabitProgressType type;
        source = (Button) event.getSource();
        type = (HabitProgressType) source.getUserData();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to delete this habit progress type?");
        alert.setContentText("This action cannot be undone.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                habitTracker.deleteHabitProgressType(type);
            }
        });
        exitProgressTypeView();
        addHabitProgressButtons();
    }

    //about frequencies
    public void openFrequencyView(ActionEvent e)
    {
        frequenceTypeAddB.setText("Add");
        addFrequencyTypeView.toFront();
        addFrequencyTypeView.setVisible(true);
        frequenceTypeDeleteB.setVisible(false);
        frequenceTypeAddB.setOnAction(this::addFrequencyClicked);
    }
    public void checkAllWeekClicked(ActionEvent event)
    {
        CheckBox source = (CheckBox) event.getSource();
        checkUncheckAllWeek(source.isSelected());

    }
    public void checkAllMonthClicked(ActionEvent event)
    {
        CheckBox source = (CheckBox) event.getSource();
        checkUncheckAllMonth(source.isSelected());

    }
    public void checkUncheckAllWeek(boolean bool)
    {
        int set = 0;
        if(bool)
        {
            set = 1;
        }
        for(int i = 0; i<7; i++)
        {
            cbWeekArr[i].setSelected(bool);
            weekOccurrences[i] = set;
        }
    }
    public void checkUncheckAllMonth(boolean bool)
    {
        int set = 0;
        if(bool)
        {
            set = 1;
        }
        for(int i = 0; i<12; i++)
        {
            cbMonthArr[i].setSelected(bool);
            monthlyOccurrences[i] = set;
        }
    }
    public void monthCBClicked(ActionEvent event)
    {
        CheckBox source = (CheckBox) event.getSource();
        int number = Integer.parseInt(source.getUserData().toString());
        if(source.isSelected())
            monthlyOccurrences[number-1]=1;
        else
            monthlyOccurrences[number-1]=0;
    }
    public void weekCBClicked(ActionEvent event)
    {
        CheckBox source = (CheckBox) event.getSource();
        int number = Integer.parseInt(source.getUserData().toString());
        if(source.isSelected())
            weekOccurrences[number-1]=1;
        else
            weekOccurrences[number-1]=0;
    }
    public void exitFrequencyView()
    {
        cbEveryDay.setSelected(false);
        cbEveryMonth.setSelected(false);
        addFrequencyTypeView.setVisible(false);
        checkUncheckAllWeek(false);
        checkUncheckAllMonth(false);
        frequenceNameTF.clear();
    }
    public void addFrequencyClicked(ActionEvent event)
    {
        String name;

        alert.setTitle("Error in adding frequency type");


        try{
            name = frequenceNameTF.getText();
        } catch (Exception x) {

            alert.setContentText("Please enter integers");
            alert.showAndWait();
            return;
        }
        if((name.isEmpty()))
        {
            alert.setContentText("Please enter name");
            alert.showAndWait();
            return;
        }
        habitTracker.addFrequency(name, weekOccurrences, monthlyOccurrences);
        exitFrequencyView();
        addFrequenceTypeButtons();
    }
    public void addFrequenceTypeButtons()
    {
        int size;
        Frequence element;

        ArrayList <Frequence> list = habitTracker.getFrequenceTypes();
        size = list.size();

        VBox vBox = new VBox();

        for(int i = 0; i<size; i++)
        {
            element = list.get(i);
            Button button = new Button(element.toString());
            button.setUserData(element);
            button.setPrefWidth(190);
            button.setOnAction(this::frequenceButtonClicked);
            vBox.getChildren().add(button);
        }
        frequenceTypeScrollPane.setContent(vBox);
    }
    public void frequenceButtonClicked(ActionEvent event)
    {
        Button source;
        String name;
        int[] weeklyOccurrence,
                monthlyOccurrence;
        Frequence frequence;


        source = (Button) event.getSource();
        frequence =(Frequence) source.getUserData();
        name = frequence.getName();
        weeklyOccurrence = frequence.getWeeklyOccurrence();
        monthlyOccurrence= frequence.getMonthlyOccurrence();

        openFrequencyView(event);
        frequenceTypeAddB.setVisible(true);
        frequenceNameTF.setText(name);

        frequenceTypeAddB.setText("Change");
        frequenceTypeAddB.setOnAction(this::changeFrequenceType);
        frequenceTypeAddB.setUserData(frequence);
        frequenceTypeDeleteB.setVisible(true);
        setWeeklyOccurrenceView(weeklyOccurrence);
        setMonthlyOccurrenceView(monthlyOccurrence);

        frequenceTypeDeleteB.setUserData(frequence);

    }
    public void changeFrequenceType(ActionEvent event)
    {
        String name;
        Button source;
        Frequence frequence;

        source = (Button) event.getSource();
        frequence = (Frequence) source.getUserData();


        alert.setTitle("Error in changing frequency type");


        name = frequenceNameTF.getText();

        if((name.isEmpty()))
        {
            alert.setContentText("Please enter name");
            alert.showAndWait();
            return;
        }
        habitTracker.changeFrequenceType(name, weekOccurrences, monthlyOccurrences,frequence);
        exitFrequencyView();
        addFrequenceTypeButtons();

    }
    public void setWeeklyOccurrenceView(int[] weeklyOccurrence)
    {
        int size,
                no;
        weekOccurrences = weeklyOccurrence;
        size = weekOccurrences.length;
        for(int i=0 ; i<size;i++)
        {
            no = weekOccurrences[i];
            if(no == 1)
            {
                cbWeekArr[i].setSelected(true);
            }
        }
    }
    public void setMonthlyOccurrenceView(int[] monthlyOccurrence)
    {
        int size,
                no;
        this.monthlyOccurrences = monthlyOccurrence;
        size = monthlyOccurrences.length;
        for(int i=0 ; i<size;i++)
        {
            no = monthlyOccurrences[i];
            if(no == 1)
            {
                cbMonthArr[i].setSelected(true);
            }
        }
    }

    public void deleteFrequency(ActionEvent event)
    {
        Button source;
        Frequence type;
        source = (Button) event.getSource();
        type = (Frequence) source.getUserData();
        confirmation.setHeaderText("Are you sure you want to delete this habit frequency type?");
        confirmation.setContentText("This action cannot be undone.");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                habitTracker.deleteFrequenceType(type);
            }
        });
        exitFrequencyView();
        addFrequenceTypeButtons();
    }

    //about habit types
    public void habitTypeViewOpen(ActionEvent e)
    {
        habitTypeView.toFront();
        habitTypeView.setVisible(true);
        setProgressBox(defaultProgressBox);
        setOccurrenceBox(defaultOccurrenceBox);
        habitTypeDeleteB.setVisible(false);
        habitTypeAddB.setOnAction(this::addHabitTypeClicked);
    }
    public void addHabitTypeClicked(ActionEvent e)
    {
        String name,
                description,
                progressType,
                frequencyType;

        alert.setTitle("Error in adding habit type");
        try{
            name = habitTypeNameTF.getText();
            description = habitTypeDescTF.getText();
            progressType = defaultProgressBox.getSelectionModel().getSelectedItem();
            frequencyType = defaultOccurrenceBox.getSelectionModel().getSelectedItem();

        } catch (Exception x) {

            alert.setContentText("Please enter integers");
            alert.showAndWait();
            return;
        }
        if((name.isEmpty()) || (description.isEmpty())||(progressType.isEmpty())||(frequencyType.isEmpty()))
        {
            alert.setContentText("Please enter everything");
            alert.showAndWait();
            return;
        }

        habitTracker.addHabitType(name,description,progressType,frequencyType);
        exitHabitTypeView();
        addHabitTypeButtons();
    }
    public void exitHabitTypeView()
    {
        habitTypeView.setVisible(false);
        defaultProgressBox.getItems().clear();
        defaultOccurrenceBox.getItems().clear();
        habitTypeNameTF.clear();
        habitTypeDescTF.clear();
    }
    public void addHabitTypeButtons()
    {
        int size;
        HabitType element;

        ArrayList <HabitType> list = habitTracker.getHabitTypes();
        size = list.size();
        System.out.println(size);

        VBox vBox = new VBox();

        for(int i = 0; i<size; i++)
        {
            element = list.get(i);
            Button button = new Button(element.toString());
            button.setUserData(element);
            button.setPrefWidth(200);
            button.setOnAction(this::habitTypeButtonClicked);
            vBox.getChildren().add(button);
        }
        habitTypeScrollPane.setContent(vBox);

    }
    public void habitTypeButtonClicked(ActionEvent event)
    {
        Button source;
        String name,
                desc;

        HabitProgressType progressType;
        Frequence frequence;
        source = (Button) event.getSource();
        HabitType type =(HabitType) source.getUserData();
        name = type.getName();
        desc = type.getDescription();
        progressType = type.getHabitProgressType();
        frequence = type.getFrequence();

        habitTypeViewOpen(event);
        habitTypeNameTF.setText(name);
        habitTypeDescTF.setText(desc);

        habitTypeDeleteB.setVisible(true);
        habitTypeAddB.setText("Change");
        habitTypeAddB.setOnAction(this::changeHabitType);
        habitTypeAddB.setUserData(type);
        habitTypeDeleteB.setUserData(type);
        defaultProgressBox.setValue(progressType.toString());
        defaultOccurrenceBox.setValue(frequence.toString());

    }
    public void changeHabitType(ActionEvent event)
    {
        String name,
                description;
        Button source;
        String frequence,
                progress;
        HabitType habitType;

        int frequenceNo,
                progressNo;

        source = (Button) event.getSource();
        habitType = (HabitType) source.getUserData();


        alert.setTitle("Error in changing habit type");


        name = habitTypeNameTF.getText();
        description = habitTypeDescTF.getText();
        progress = defaultProgressBox.getSelectionModel().getSelectedItem();
        frequence = defaultOccurrenceBox.getSelectionModel().getSelectedItem();

        frequenceNo = Integer.parseInt(frequence.substring(0,1));
        progressNo = Integer.parseInt(progress.substring(0,1));

        if((name.isEmpty()) || (description.isEmpty()))
        {
            alert.setContentText("Please enter name");
            alert.showAndWait();
            return;
        }
        habitTracker.changeHabitType(name, description, frequenceNo,progressNo,habitType);
        exitHabitTypeView();
        addHabitTypeButtons();
    }



    public void deleteHabitTypes(ActionEvent event)
    {
        Button source;
        HabitType type;
        source = (Button) event.getSource();
        type = (HabitType) source.getUserData();
        confirmation.setHeaderText("Are you sure you want to delete this habit frequency type?");
        confirmation.setContentText("This action cannot be undone.");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                habitTracker.deleteHabitType(type);
            }
        });
        exitHabitTypeView();
        addHabitTypeButtons();
    }
    //both habits and habit types
    public void setOccurrenceBox(ChoiceBox<String> choiceBox)
    {
        ArrayList<Frequence> list = habitTracker.getFrequenceTypes();
        for(Frequence element: list)
        {
            choiceBox.getItems().add(element.toString());
        }
    }

    public void setHabitTypeBox(ChoiceBox<String> choiceBox)
    {
        ArrayList<HabitType> list = habitTracker.getHabitTypes();
        for(HabitType element: list)
        {
            choiceBox.getItems().add(element.toString());
        }
    }

    public void setProgressBox(ChoiceBox<String> choiceBox)
    {
        ArrayList<HabitProgressType> list = habitTracker.getHabitProgressTypes();
        for(HabitProgressType element: list)
        {
            choiceBox.getItems().add(element.toString());
        }
    }

    //habits
    //change and add at the same func make it like that !!!!
    public void addHabitClicked(ActionEvent e)
    {
        // check
        String name,
                description,
                habitType,
                progressType,
                frequencyType;
        int habitTypeNo,
                habitProgressNo,
                frequencyNo;

        alert.setTitle("Error in adding habit type");

        HabitType type;
        Frequence frequence;
        HabitProgressType habitProgressType;

        name = habitNameTF.getText();
        description = habitDescTF.getText();
        habitType = hHabitTypeChooser.getSelectionModel().getSelectedItem();
        progressType = hProgressTypeChooser.getSelectionModel().getSelectedItem();
        frequencyType = hFrequenceChooser.getSelectionModel().getSelectedItem();

        if((name.isEmpty()) || (description.isEmpty()) || (habitType == null))
        {
            alert.setContentText("Please enter name, description and habit type");
            alert.showAndWait();
            return;
        }
        habitTypeNo =  Integer.parseInt(habitType.substring(0,1));
        type = habitTracker.getSpecificHabitType(habitTypeNo);
        //if description and is empty then they are default !!!!
        if(frequencyType==null)
        {
            frequence = type.getFrequence();
        }
        else
        {
            frequencyNo = Integer.parseInt(frequencyType.substring(0,1));
            frequence = habitTracker.getSpecificFrequence(frequencyNo);
        }
        if(progressType == null)
        {
            habitProgressType = type.getHabitProgressType();
        }
        else
        {
            habitProgressNo = Integer.parseInt(progressType.substring(0,1));
            habitProgressType = habitTracker.getSpecificHabitProgressType(habitProgressNo);
        }

        habitTracker.addHabit(name,description,type,habitProgressType,frequence);
        closeHabitView();
        setSmallHabitPane();
        setUpAllHabitScrollFrame();
        //addHabitTypeButtons();

    }

    public void deleteHabit(ActionEvent event)
    {
        Habit habit;
        Button source;
        source = (Button) event.getSource();
        habit = (Habit) source.getUserData();

        confirmation.setHeaderText("Are you sure you want to delete this habit?");
        confirmation.setContentText("This action cannot be undone.");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                habitTracker.deleteHabit(habit);
            }
        });
        closeHabitDetailedView(event);
        setSmallHabitPane();
        setUpAllHabitScrollFrame();

    }

    public void changeHabit(ActionEvent e)
    {
        String name,
                description;
        Button source;
        Habit habit;

        int frequenceNo,
                progressNo;

        source = (Button) e.getSource();
        habit = (Habit) source.getUserData();


        alert.setTitle("Error in changing habit");


        name = habitNameTFDetailed.getText();
        description = habitDescTFDetailed.getText();


        if((name.isEmpty()) || (description.isEmpty()))
        {
            alert.setContentText("Please enter name and description");
            alert.showAndWait();
            return;
        }
        habitTracker.changeHabit(name,description,habit);
        closeHabitDetailedView(e);
        setSmallHabitPane();
        setUpAllHabitScrollFrame();

    }



    public void setSmallHabitPane()
    {
        int size;
        HabitButton element;

        InitializeHabitButtons(habitButtons);

        size = habitButtons.size();
        VBox vBox = new VBox();

        for(int i = 0; i<size; i++)
        {
            element = habitButtons.get(i);
            element.setPrefWidth(240);
            vBox.getChildren().add(element);
        }
        habitsSmallScrollPane.setContent(vBox);
    }

    public void closeHabitDetailedView(ActionEvent event)
    {

        habitDetailedView.setVisible(false);


    }

    public void exitPopUp()
    {
        exitProgressTypeView();
        exitFrequencyView();
        exitHabitTypeView();
        closeHabitView();
        closeHabitDetailedView(null);
        habitInfoView.setVisible(false);
    }
}
