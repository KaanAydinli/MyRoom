package com.habittracker;

public class HabitProgressType
{
    private String name;
    private int progressNo,
                progress,
                progressCoefficient;

    public HabitProgressType(String name,int progressNo, int progress, int progressCoefficient)
    {
        this.name = name;
        this.progressNo = progressNo;
        this.progress = progress;
        this.progressCoefficient = progressCoefficient;
    }
    public void changeProgressType(String name, int progress, int progressCoefficient)
    {
        this.name = name;
        this.progress = progress;
        this.progressCoefficient = progressCoefficient;
    }
    public String getName()
    {
        return name;
    }

    public int getProgress()
    {
        return progress;
    }

    public int getProgressCoefficient()
    {
        return progressCoefficient;
    }

    public int getProgressNo()
    {
        return progressNo;
    }

    @Override
    public String toString()
    {
        String output;
        output = String.format("%d, %s",progressNo,name);
        return output;
    }


}
