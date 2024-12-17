package com.calendarfunctionality;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.sound.midi.SysexMessage;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Calendar
{
    private int userId,
                calendarNo;
    private boolean visibility;

    private String name,
                   description,
                    taskListJSON;

    private final String DATABASE = "jdbc:sqlite:MyRooms/src/main/resources/TemporaryUserDB/calendar_database.sqlite";

    private ArrayList<Task> tasks = new ArrayList<>();

    Gson gson = new Gson();
    Connection connection;



    public Calendar(int userId,int calendarId,String name,String description,boolean visibility, String taskList, Connection connection)
    {
        this.userId = userId;
        this.calendarNo = calendarId;
        this.visibility = visibility;
        this.name = name;
        this.description = description;
        this.taskListJSON = taskList;
        this.connection = connection;
    }


    public void setUpTasks(ArrayList<Task> allTasks)
    {
        int size,
            currentNo;
        Task currentTask;
        Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();

        ArrayList<Integer> taskNos;
        taskNos = gson.fromJson(taskListJSON, listType);
        if(taskNos == null)
        {
            taskNos = new ArrayList<>();
        }

        size = allTasks.size();
        for(int i = 0; i < size; i++)
        {
            currentTask = allTasks.get(i);
            currentNo = currentTask.getTaskNo();
            if(taskNos.contains(currentNo))
            {
                tasks.add(currentTask);
            }
        }

    }

    public void addTask(Task task)
    {
        int size,
                currentNo;
        String output;

        Task currentTask;
        Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();
        ArrayList<Integer> taskNos;
        if(taskListJSON.isEmpty())
        {
            taskNos = new ArrayList<>();
        }
        else
        {
            taskNos = gson.fromJson(taskListJSON, listType);
        }

        tasks.add(task);
        taskNos.add(task.getTaskNo());

        output = gson.toJson(taskNos);
        taskListJSON = output;
        String sql = "UPDATE CalendarDb SET taskList = ? WHERE userId = ? and calendarNo = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, output);
            pstmt.setDouble(2, userId);
            pstmt.setInt(3, calendarNo);

            // Execute the update
            pstmt.executeUpdate();



        } catch (Exception e) {
            System.out.println("Error in adding task in calendar");
        }
    }



    public int getCalendarNo()
    {
        return calendarNo;
    }

    public String getName()
    {
        return name;
    }
    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString()
    {
        String output;
        output = String.format("%d,%s",calendarNo,name);
        return output;
    }

    public ArrayList<Task> getTasks()
    {
        return tasks;
    }

}
