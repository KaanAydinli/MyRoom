package com.example.myrooms;

import com.calendarfunctionality.*;
import com.habittracker.HabitProgressType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class CalendarController extends Pane
{
    CalendarFunctionality calendarFunctionality;

    private Node innerPane;

    @FXML
    private Pane datePickerCalendar,
            mainViewPanel;

    @FXML
    private Node calendarView,
                 taskView;

    @FXML
    private TextField calendarNameTF,
                      calendarDescriptionTF,
                      taskNameTF,
                      taskStartHTF,
                      taskEndHTF,
                      taskStartMTF,
                      taskEndMTF;

    @FXML
    private CheckBox taskDoneCB,
                    taskAllDayCB,
                    taskDailyTaskCB;
    @FXML
    private DatePicker taskDatePicker,
                       currentDatePicker;

    @FXML
    private ComboBox<String> taskNotificationComboBox;

    @FXML
    private ComboBox<String> reoccurrenceComboBox,
                             calendarComboBox,
                            viewChoiceComboBox;

    @FXML
    private Button calendarDeleteB;

    @FXML
    private ScrollPane calendarScrollPane,
                        dailyViewScrollPane;

    private LocalDate currentDate,
                        dailyViewDate;

    private int mode; //0 --> weekly 1--> daily

    private MonthlyView monthlyView;
    private DailyView dailyView;
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);

    private ArrayList<CalendarButton> calendarButtons = new ArrayList<>();

    private int userID;

    public CalendarController(Pane Window, int userID)
    {
        this.setPrefSize(900,580);
        this.setLayoutX(100);
        this.setLayoutY(15);
        this.userID = userID;
        calendarFunctionality = new CalendarFunctionality(this.userID);
        this.setVisible(false);
        Window.getChildren().add(this);
        loadFXML();
        setUpDatePicker();
        setUpCalendarButtons();
        setUpCalendarScrollPane();
        setCalendarComboBox();
        setNotificationComboBox();
        currentDate = LocalDate.now();
        monthlyView = new MonthlyView(currentDate.getMonthValue(),currentDate.getYear());
        setUpMonthlyView();
        this.mode = 0;
        currentDatePicker.setValue(LocalDate.now());
        setUpView();
        dailyViewDate = currentDate;
        dailyView = new DailyView(calendarFunctionality.getSpecificDay(currentDate));
        dailyView.setDailyViewView();
        setChoiceComboBox();

        currentDatePicker.setDisable(true);
    }
    private void loadFXML()
    {
        String location = String.format("Calendars/%s.fxml", "calendar_default");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
            loader.setController(this);
            innerPane = loader.load();
            this.getChildren().add(innerPane);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML", e);
        }
    }
    public void openCalendar()
    {
        this.setVisible(true);
        this.toFront();
        //setting up the view
    }
    public void closeCalendar()
    {
        this.setVisible(false);
    }

    public class DailyView extends Pane
    {

        Label dateLabel = new Label();
        VBox allDayTasks = new VBox();
        LocalDate date;
        Day day;

        public DailyView(Day day)
        {
            this.day = day;
            dateLabel.setText(day.getDate().toString());
            setGeneralView();
            this.date = day.getDate();
        }
        public void changeDay(LocalDate date)
        {
            this.day = calendarFunctionality.getSpecificDay(date);
            this.date = date;
            this.dateLabel.setText(day.getDate().toString());
            setGeneralView();
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
                button.setOnAction(CalendarController.this::taskClicked);
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
            dailyViewScrollPane.setOnMouseClicked(CalendarController.this::taskViewAddOpen);
        }
    }
    public class MonthlyView extends GridPane
    {
        ArrayList<DailyMonthFrame> dayFrames = new ArrayList<>();
        ArrayList<Day> allDays = new ArrayList<>();
        Month month;
        public MonthlyView(int month, int year)
        {
            this.month = calendarFunctionality.getSpecificMonth(month,year);
            allDays = this.month.getDays();
            setUpMonthlyView();
        }

        public void setUpMonthlyView()
        {
            int row,
                column,
                size;
            DailyMonthFrame currentFrame;
            row = 0;
            column = 0;
            setUpDayFrames();
            size = dayFrames.size();
            this.getChildren().clear();
            this.setHgap(5);
            this.setVgap(5);

            for(int i = 0; i < size; i++)
            {
                currentFrame = dayFrames.get(i);
                if(column >6)
                {
                    column = 0;
                    row += 1;
                }
                this.add(currentFrame,column,row);
                column += 1;

            }
        }

        public void changeMonth(int month, int year)
        {
            this.month = calendarFunctionality.getSpecificMonth(month,year);
            allDays = this.month.getDays();
            setUpMonthlyView();
        }

        public void setUpDayFrames()
        {
            int size;
            Day currentDay;
            size = allDays.size();
            dayFrames.clear();
            for(int i = 0; i < size; i++)
            {
                currentDay = allDays.get(i);
                DailyMonthFrame dayFrame = new DailyMonthFrame(currentDay);
                dayFrames.add(dayFrame);
            }
        }
        public Month getMonth()
        {
            return month;
        }
    }
    public class DailyMonthFrame extends VBox
    {
        Day day;
        Label date;
        ScrollPane innerBox;
        public DailyMonthFrame(Day day)
        {
            this.day = day;
            date = new Label(day.toString());
            innerBox = new ScrollPane();
            innerBox.setMaxHeight(75);
            innerBox.setMinHeight(75);
            innerBox.setMinWidth(80);
            innerBox.setMinWidth(80);
            innerBox.setOnMouseClicked(CalendarController.this::taskViewAddOpen);
            setUpTasks();
            this.getChildren().add(date);
            this.getChildren().add(innerBox);
        }
        public void setUpTasks()
        {
            int size;
            Button button;
            Task task;
            ArrayList<Task> tasks = day.getTasks();
            VBox innerVBox = new VBox();
            size = tasks.size();

            for(int i = 0; i < size; i++)
            {
                task = tasks.get(i);
                button = new Button();
                button.setText(task.toString());
                button.setUserData(task);
                button.setOnAction(CalendarController.this::taskClicked);
                innerVBox.getChildren().add(button);
            }
            innerBox.setContent(innerVBox);
        }
    }
    public class CalendarButton extends GridPane
    {
        Calendar calendar;
        CheckBox  checkBox;
        Button calendarButton;

        public CalendarButton(Calendar calendar)
        {
            this.calendar = calendar;
            checkBox = new CheckBox();
            calendarButton = new Button(calendar.getName());
            calendarButton.setMinWidth(150);
            calendarButton.setOnAction(this::calendarClicked);
            checkBox.setStyle("-fx-font-size: 18px;");
            this.add(calendarButton,0,0);
            this.add(checkBox,1,0);

        }
        public void calendarClicked(ActionEvent event)
        {
            calendarNameTF.setText(calendar.getName());
            calendarDescriptionTF.setText(calendar.getDescription());
            openCalendarView(event);
            calendarDeleteB.setVisible(true);
            calendarDeleteB.setUserData(calendar);
        }

    }
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
        taskDailyTaskCB.setSelected(task.getDailyTask());
    }
    public void setUpView()
    {
        if(this.mode == 0)
        {
            setUpMonthlyView();
        }
        else
        {
            setUpDailyView();
        }
    }
    public void setUpMonthlyView()
    {
        dailyViewScrollPane.setVisible(false);
        mainViewPanel.getChildren().clear();
        mainViewPanel.setVisible(true);
        monthlyView.setUpMonthlyView();
        mainViewPanel.getChildren().add(monthlyView);
        monthlyView.setMaxWidth(Double.MAX_VALUE);
        monthlyView.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(monthlyView, Priority.ALWAYS);
    }
    public void setUpDailyView()
    {
        this.mode = 1;
        dailyViewScrollPane.setVisible(true);
        mainViewPanel.setVisible(false);
        dailyView.setGeneralView();

    }
    public void taskViewAddOpen(MouseEvent event)
    {
        taskViewClose();
        taskView.toFront();
        taskView.setVisible(true);
        event.consume();
    }
    public void changeViewCB()
    {
        String choice;
        choice = viewChoiceComboBox.getValue();

        if(choice.equals("Month"))
        {
            setUpMonthlyView();
            this.mode = 0;
        }
        else
        {
            setUpDailyView();

        }
    }
    public void prevViewClicked(ActionEvent event)
    {
        int month,
                year;
        if(mode == 0) {
            month = monthlyView.getMonth().getMonth();
            year = monthlyView.getMonth().getYear();
            if (month - 1 < 1)
            {
                month = 12;
                year -= 1;
            }
            else
            {
                month--;
            }
            monthlyView.changeMonth(month,year);
        }
        else
        {
            dailyViewDate = dailyViewDate.minusDays(1);
            dailyView.changeDay(dailyViewDate);
        }

    }
    public void nextViewClicked(ActionEvent event)
    {
        if(mode == 0)
        {
            int month = monthlyView.getMonth().getMonth();
            int year = monthlyView.getMonth().getYear();
            if(month + 1 >12)
            {
                month = 1;
                year += 1;
            }
            else
            {
                month++;
            }
            monthlyView.changeMonth(month,year);
        }
        else
        {
            dailyViewDate = dailyViewDate.plusDays(1);
            dailyView.changeDay(dailyViewDate);
        }
    }
    public void taskViewOpen(ActionEvent event)
    {
        taskView.setVisible(true);
        System.out.println("taskViewOpen");
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
        boolean done,
                allDay,
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
            done = taskDoneCB.isSelected();
            allDay = taskAllDayCB.isSelected();
            dailyTask = taskDailyTaskCB.isSelected();
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
        calendarFunctionality.addTask(name,date,calendar,startH, startM,endH, endM, dailyTask, allDay, notificationM);
        taskViewClose();
        setUpView();

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
    public void setChoiceComboBox()
    {
        viewChoiceComboBox.getItems().clear();
        viewChoiceComboBox.getItems().add("Month");
        viewChoiceComboBox.getItems().add("Day");
        viewChoiceComboBox.setValue("Month");
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
    public void taskViewClose()
    {
        taskView.setVisible(false);
        taskNameTF.clear();
        taskDoneCB.setSelected(false);
        taskAllDayCB.setSelected(false);
        taskDailyTaskCB.setSelected(false);
        taskDatePicker.setValue(null);
        taskStartHTF.clear();
        taskEndHTF.clear();
        taskStartMTF.clear();
        taskEndMTF.clear();
        taskNotificationComboBox.setValue(null);
        calendarComboBox.setValue(null);
    }
    public void setUpCalendarButtons()
    {
        int size;
        CalendarButton calendarButton;
        Calendar calendar;
        ArrayList<Calendar> calendars = calendarFunctionality.getCalendars();
        size = calendars.size();
        calendarButtons.clear();

        for(int i = 0; i < size; i++)
        {
            calendar = calendars.get(i);
            calendarButton = new CalendarButton(calendar);
            calendarButtons.add(calendarButton);
        }
    }
    public void setUpCalendarScrollPane()
    {
        int size;
        setUpCalendarButtons();
        size = calendarButtons.size();
        VBox vBox = new VBox();
        CalendarButton button;

        for(int i = 0; i < size; i++)
        {
            button = calendarButtons.get(i);
            vBox.getChildren().add(button);
        }
        calendarScrollPane.setContent(vBox);


    }
    public void setUpDatePicker()
    {
        DatePicker datePicker = new DatePicker();

        // Access the internal calendar view
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Region popupContent = (Region) datePickerSkin.getPopupContent();

        // Display only the calendar view in a layout
        datePickerCalendar.getChildren().add(popupContent);
    }
    public void openCalendarView(ActionEvent event)
    {
        calendarDeleteB.setVisible(false);
        calendarView.setVisible(true);
    }
    public void addingCalendarClicked(ActionEvent event)
    {
        String name,
                description;

        alert.setTitle("Error in adding calendar");
        alert.setHeaderText("Error Details");


        name = calendarNameTF.getText();
        description = calendarDescriptionTF.getText();

        if(description.isEmpty()||name.isEmpty())
        {
            alert.setContentText("Can not be blank");
            alert.showAndWait();
            return;

        }
        calendarFunctionality.addCalendar(name,description);
        exitCalendarView();
        setUpCalendarButtons();
        setUpCalendarScrollPane();
        setCalendarComboBox();
        setUpView();

    }

    public void deleteCalendarClicked(ActionEvent event)
    {
        Button source;
        Calendar calendar;
        source = (Button) event.getSource();
        calendar =(Calendar) source.getUserData();

        confirmation.setHeaderText("Are you sure you want to delete this calendar?");
        confirmation.setContentText("This action cannot be undone.");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                calendarFunctionality.deleteCalendar(calendar);
            }
        });
        exitCalendarView();
        setUpCalendarScrollPane();
    }
    public void exitCalendarView()
    {
        calendarView.setVisible(false);
        calendarDescriptionTF.clear();
        calendarNameTF.clear();

    }
    public Day getToday()
    {
        Day today;
        today = calendarFunctionality.getSpecificDay(currentDate);
        return today;
    }

    public void exitPopUp()
    {
        exitCalendarView();
        taskViewClose();
    }


}
