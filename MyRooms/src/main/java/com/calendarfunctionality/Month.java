package com.calendarfunctionality;


import java.util.ArrayList;

public class Month
{
    int month,
            year;
    ArrayList<Day> monthDays = new ArrayList<>();
    public Month(int month, int year)
    {
        this.month = month;
        this.year = year;
    }
    public void setDays(ArrayList<Day> days)
    {
        this.monthDays = days;
    }
    public ArrayList<Day> getDays()
    {
        return monthDays;
    }
    public int getMonth()
    {
        return month;
    }
    public int getYear()
    {
        return year;
    }
}
