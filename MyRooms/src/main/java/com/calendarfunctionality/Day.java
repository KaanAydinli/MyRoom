package com.calendarfunctionality;


import java.time.LocalDate;
import java.util.ArrayList;

public class Day
{
    LocalDate date;
    ArrayList<Task> tasks = new ArrayList<>();
    public Day(LocalDate date)
    {
        this.date = date;
    }

    public void addTasks(Task task)
    {
        System.out.println("Adding task: " + task);
        tasks.add(task);
    }

    public ArrayList<Task> getTasks()
    {
        return tasks;
    }
    @Override
    public String toString()
    {
        return date.toString();
    }

    public LocalDate getDate()
    {
        return date;
    }
}
