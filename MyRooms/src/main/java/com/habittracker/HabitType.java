package com.habittracker;

import com.example.myrooms.Frequence;

public class HabitType
{
    private int id,
                habitTypeNo;
    private String name,
                    description;
    private Frequence frequence;
    private HabitProgressType habitProgressType;

    public HabitType(int id, int habitTypeNo, String name,String description, Frequence frequence, HabitProgressType habitProgressType)
    {
        this.id = id;
        this.habitTypeNo = habitTypeNo;
        this.name = name;
        this.description = description;
        this.frequence = frequence;
        this.habitProgressType = habitProgressType;
    }

    public int getHabitTypeNo()
    {
        return habitTypeNo;
    }
    public String getName()
    {
        return name;
    }

    public String getDescription(){return description;}
    public Frequence getFrequence()
    {
        return frequence;
    }

    public HabitProgressType getHabitProgressType()
    {
        return habitProgressType;
    }

    public void changeHabitType(String name, String description, Frequence frequence, HabitProgressType habitProgressType)
    {
        this.name = name;
        this.description = description;
        this.frequence = frequence;
        this.habitProgressType = habitProgressType;
    }
    @Override
    public String toString()
    {
        String output;
        output = String.format("%d, %s",habitTypeNo,name);
        return output;
    }
}
