package com.calendarfunctionality;

import com.example.myrooms.Frequence;
import com.habittracker.Habit;
import com.habittracker.HabitProgressType;
import com.habittracker.HabitType;
import javafx.event.ActionEvent;

import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarFunctionality
{
    private int userId;
    private final String DATABASE = "jdbc:sqlite:MyRooms/src/main/resources/TemporaryUserDB/calendar_database.sqlite";

    Connection connection;
    LocalDate currentDate;
    LocalDate endDate = LocalDate.ofYearDay(2026,365);
    LocalDate startDate = LocalDate.ofYearDay(2023,1);

    ArrayList<Calendar> calendars = new ArrayList<>();
    ArrayList<Day> allDays = new ArrayList<>();
    ArrayList<Month> allMonths = new ArrayList<>();

    public CalendarFunctionality(int userId)
    {
        this.userId = userId;
        this.connection = connect();
        setUpCalendar();
        setUpDates();
        setUpTasksDatesNCalendars();
    }
    public Connection connect()
    {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
        return connection;
    }
    public void setUpDates()
    {

        Month month;
        Day day;
        YearMonth currentMonth = YearMonth.from(startDate);
        int monthVal,
            yearVal;

        while (!currentMonth.isAfter(YearMonth.from(endDate)))
        {
            LocalDate firstDayOfMonth = currentMonth.atDay(1);
            LocalDate lastDayOfMonth = currentMonth.atEndOfMonth();

            LocalDate firstValidDay = firstDayOfMonth.isBefore(startDate) ? startDate : firstDayOfMonth;
            LocalDate lastValidDay = lastDayOfMonth.isAfter(endDate) ? endDate : lastDayOfMonth;

            monthVal = currentMonth.getMonthValue();
            yearVal = currentMonth.getYear();

            month = new Month(monthVal, yearVal);
            ArrayList<Day> days = new ArrayList<>();
            for (LocalDate date = firstValidDay; !date.isAfter(lastValidDay); date = date.plusDays(1))
            {
                day = new Day(date);
                days.add(day);
                allDays.add(day);
            }
            month.setDays(days);
            allMonths.add(month);
            currentMonth = currentMonth.plusMonths(1);
        }


    }
    public void setUpTasksDatesNCalendars()
    {
        int calendarSize,
            tasksSize;
        Calendar calendar;
        ArrayList<Task> tasks = setUpTasks();
        calendarSize = calendars.size();
        tasksSize = tasks.size();
        //setting up calendar tasks
        for(int i = 0; i < calendarSize; i++)
        {
            calendar = calendars.get(i);
            calendar.setUpTasks(tasks);
        }
        for(int i = 0; i < tasksSize; i++)
        {
            addTaskToDay(tasks.get(i));
        }

        //setting up dates


        //then tasks to dates

    }

    public ArrayList<Task> setUpTasks()
    {
        int taskNo,
            taskStartH,
            taskStartM,
            taskEndH,
            taskEndM,
            notificationTime;
        boolean dailyTask,
                allDay,
                isDone;
        String name;
        LocalDate date;


        ArrayList<Task> tasks = new ArrayList<>();
        String sql = "SELECT userId, taskNo, name, taskStartH, taskStartM, taskEndH, taskEndM,allDay,isDone,notificationTime, Date FROM taskDB WHERE userId = ?";



        try (PreparedStatement pstmt  = connection.prepareStatement(sql))
        {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                taskNo = rs.getInt("taskNo");
                name = rs.getString("name");
                taskStartH = rs.getInt("taskStartH");
                taskStartM = rs.getInt("taskStartM");
                taskEndH = rs.getInt("taskEndH");
                taskEndM = rs.getInt("taskEndM");
                allDay = rs.getBoolean("allDay");
                isDone = rs.getBoolean("isDone");
                notificationTime = rs.getInt("notificationTime");
                date = LocalDate.parse(rs.getString("Date"));
                Task task = new Task(userId, taskNo,name, date, taskStartH, taskStartM,taskEndH,taskEndM,allDay,isDone,notificationTime);
                tasks.add(task);
            }

        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return tasks;
    }
    public void addTask(String name,LocalDate date,Calendar calendar, int startH, int startM, int endH, int endM,boolean isAllDay, int notificationM)
    {
        int taskNo;
        String query = "INSERT INTO taskDB(userId,taskNo,name,taskStartH,taskStartM,taskEndH,taskEndM,allDay,isDone,notificationTime,Date) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query))
        {
            taskNo = getMaxTaskNo()+ 1;
            // Set the values for the query parameters
            pstmt.setInt(1, this.userId);
            pstmt.setInt(2, taskNo );
            pstmt.setString(3, name);
            pstmt.setInt(4,startH);
            pstmt.setInt(5,startM);
            pstmt.setInt(6,endH);
            pstmt.setInt(7,endM);
            pstmt.setInt(8,isAllDay? 1 : 0);
            pstmt.setInt(9,0);
            pstmt.setInt(10,notificationM);
            pstmt.setString(11,date.toString());
            pstmt.executeUpdate();

            Task task = new Task(userId, taskNo,name, date, startH, startM,endH,endM,isAllDay,false,notificationM);
            addTaskToDay(task);
            calendar.addTask(task);
        }
        catch (Exception e)
        {
            System.out.println("Error adding task: " + e.getMessage());
        }
    }

    public void addTaskToDay(Task task)
    {
        List<Day> matchingDays = allDays.stream()
                .filter(day -> day.getDate().equals(task.getDate()))
                .toList();
        // Print the matching Days
        if (!matchingDays.isEmpty()) {
            matchingDays.forEach(day -> day.addTasks(task));
        } else {
            System.out.println("No matching days found.");
        }

    }

    public void  removeTaskFromDay(Task task)
    {
        List<Day> matchingDays = allDays.stream()
                .filter(day -> day.getDate().equals(task.getDate()))
                .toList();
        // Print the matching Days
        if (!matchingDays.isEmpty()) {
            matchingDays.forEach(day -> day.removeTask(task));
        } else {
            System.out.println("No matching days found.");
        }

    }


    public Calendar getTaskCalendar(Task task)
    {
        ArrayList<Task> tasks;
        int size;
        Calendar calendar;
        size = calendars.size();
        for(int i = 0; i < size; i++)
        {
            calendar = calendars.get(i);
            tasks = calendar.getTasks();
            if(tasks.contains(task))
            {
                return calendar;
            }
        }
        return null;
    }

    public Day getSpecificDay(LocalDate date)
    {
        Day d;
        int size;
        LocalDate currentDate;
        size = allDays.size();
        for(int i = 0; i < size; i++)
        {
            d = allDays.get(i);
            currentDate = d.getDate();
            if(currentDate.isEqual(date))
            {
                return d;
            }
        }
        return null;
    }
    public Month getSpecificMonth(int month, int year)
    {
        int size,
                monthVal,
                yearVal;
        Month m;

        size = allMonths.size();
        for(int i = 0; i<size; i++)
        {
            m = allMonths.get(i);
            monthVal = m.getMonth();
            yearVal = m.getYear();
            if(monthVal == month && yearVal == year)
            {
                return m;
            }
        }
        return null;
    }
    public int getMaxTaskNo()
    {
        int max;
        String sql = "SELECT MAX(taskNo) AS max_value FROM taskDB"; // Replace your_column and your_table

        max = 0;
        try (Connection conn = DriverManager.getConnection(DATABASE);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Check if a result exists
            if (rs.next()) {
                max = rs.getInt("max_value"); // Change data type if necessary
            } else {
                max = 0;
            }
        } catch (Exception e) {
            System.out.println("Error getting max value: " + e.getMessage());
            max = 0;
        }
        return max;
    }
    public void addCalendar(String name, String description)
    {
        int calendarNo;
        boolean visibility;

        String query = "INSERT INTO CalendarDB(userId,calendarNo,name,description,visibility,taskList) VALUES (?,?,?,?,?,?)";


        try (PreparedStatement pstmt = connection.prepareStatement(query))
        {
            calendarNo = getMaxCalendarNo()+ 1;
            // Set the values for the query parameters
            pstmt.setInt(1, this.userId);
            pstmt.setInt(2, calendarNo);
            pstmt.setString(3, name);
            pstmt.setString(4,description);
            pstmt.setInt(5,1);
            pstmt.setString(6,"");

            pstmt.executeUpdate();
            Calendar calendar = new Calendar(userId,calendarNo,name,description,true,"",connection);
            calendars.add(calendar);
        }
        catch (Exception e)
        {
            System.out.println("Error adding frequency: " + e.getMessage());
        }
    }
    public void setUpCalendar()
    {
        String sql = "SELECT userId,calendarNo,name,description,visibility,taskList FROM CalendarDb WHERE userId = ?";
        String name,
                description,
                taskList;
        int     calendarNo,
                visibInt;
        boolean visb;

        try (PreparedStatement pstmt  = connection.prepareStatement(sql))
        {

            pstmt.setInt(1, this.userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                calendarNo = rs.getInt("calendarNo");
                name = rs.getString("name");
                description = rs.getString("description");
                visibInt = rs.getInt("visibility");
                visb = visibInt == 1;
                taskList = rs.getString("taskList");

                Calendar calendar = new Calendar(this.userId,calendarNo,name,description,visb,taskList,connection);
                calendars.add(calendar);
            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTask(Task task)
    {
        String deleteSQL = "DELETE FROM TaskDb WHERE userId = ? AND taskNo = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, task.getTaskNo() );
            pstmt.executeUpdate();
            Calendar calendar = getTaskCalendar(task);
            calendar.deleteTask(task);
            removeTaskFromDay(task);
        } catch (SQLException e) {
            System.out.println("Problem in deleting habit progress type");
        }
    }

    public void deleteCalendar(Calendar calendar)
    {
        String deleteSQL = "DELETE FROM CalendarDb WHERE userId = ? AND calendarNo = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, calendar.getCalendarNo() );
            pstmt.executeUpdate();
            ArrayList<Task> tasks = calendar.getTasks();
            int size = tasks.size();
            for(int i = 0; i < size; i++)
            {
                Task task = tasks.get(i);
                this.deleteTask(task);
                size-=1;
            }
            calendars.remove(calendar);

        } catch (SQLException e) {
            System.out.println("Problem in deleting habit progress type");
        }

    }
    public int getMaxCalendarNo()
    {
        int size,
            max;
        size = calendars.size();
        if(size == 0)
        {
            return 0;
        }
        max = calendars.get(0).getCalendarNo();
        return max;
    }

    public void changeTask(String name,LocalDate date , Calendar calendar, int startH, int startM, int endH, int endM,boolean allDay,boolean isDone, int notificationM,Task task)
    {
        int no;
        no = task.getTaskNo();

        String updateSQL = "UPDATE taskDB SET name = ?, taskStartH = ?, taskStartM = ?, taskEndH = ?, taskEndM = ?, allDay = ?, isDone = ?, notificationTime = ?, Date = ? WHERE userId = ? AND taskNo = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setInt(10, userId);
            pstmt.setInt(11, no);

            pstmt.setString(1, name);
            pstmt.setInt(2, startH);
            pstmt.setInt(3, startM );
            pstmt.setInt(4, endH);
            pstmt.setInt(5,endM);
            pstmt.setInt(6,allDay ? 1 : 0);
            pstmt.setInt(7,isDone ? 1 : 0);
            pstmt.setInt(8,notificationM);
            pstmt.setString(9,date.toString());
            pstmt.executeUpdate();
            task.changeTask(name, date, startH, startM, endH, endM, allDay, isDone, notificationM);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error changing task" + e.getMessage());;
        }
    }

    public Calendar getSpecificCalendar(int calendarNo)
    {
        Calendar calendar;
        int size;
        size = calendars.size();
        for(int i = 0; i < size; i++)
        {
            calendar = calendars.get(i);
            if(calendarNo == calendar.getCalendarNo())
            {
                return calendar;
            }
        }
        return null;
    }

    public ArrayList<Calendar> getCalendars()
    {
        return calendars;
    }

    public int getCalendarSize()
    {
        return calendars.size();
    }



}
