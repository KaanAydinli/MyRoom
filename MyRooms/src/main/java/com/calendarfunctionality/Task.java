package com.calendarfunctionality;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class Task
{
    private int id,
                taskNo,
                startH,
                startM,
                endH,
                endM,
                notificationM;
    boolean allDay,
            isDone;


    String name;
    LocalDate date;

    public Task(int id, int taskNo,String name, LocalDate date, int startH, int startM, int endH, int endM, boolean isAllDay,boolean isDone, int notificationM)
    {
        this.id = id;
        this.taskNo = taskNo;
        this.name = name;
        this.date = date;
        this.startH = startH;
        this.startM = startM;
        this.endH = endH;
        this.endM = endM;

        this.allDay = isAllDay;
        this.notificationM = notificationM;
    }

    public void changeTask(String name,LocalDate date ,int startH, int startM, int endH, int endM,boolean allDay,boolean isDone, int notificationM)
    {
        this.name = name;
        this.date = date;
        this.startH = startH;
        this.startM = startM;
        this.endH = endH;
        this.endM = endM;
        this.allDay = allDay;
        this.isDone = isDone;
        this.notificationM = notificationM;
    }

    public boolean getIsDone()
    {
        return isDone;
    }

    public int getTaskNo()
    {
        return taskNo;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        String output;
        output = String.format("%d,%s",taskNo,name);
        return output;
    }

    public boolean isDone()
    {
        return isDone;
    }
    public boolean isAllDay()
    {
        return allDay;
    }

    public LocalDate getDate()
    {
        return date;
    }
    public int getStartH(){
        return startH;
    }
    public int getStartM(){
        return startM;
    }
    public int getEndH(){
        return endH;
    }
    public int getEndM(){
        return endM;
    }
    public int getNotificationM(){
        return notificationM;
    }

}
