package com.example.myrooms;

public class Frequence
{
    private int id,
                frequenceNo;
    private String name;

    private int[] weeklyOccurrence;
    private int[] monthlyOccurrence;

    public Frequence(int id, int frequenceNo,String name, int[] weeklyOccurrence, int[] monthlyOccurrence)
    {
        this.id = id;
        this.frequenceNo = frequenceNo;
        this.name = name;
        this.weeklyOccurrence = weeklyOccurrence;
        this.monthlyOccurrence = monthlyOccurrence;
    }

    public Frequence(int id, int frequenceNo, String name, String weeklyOccurrence, String monthlyOccurrence)
    {
        this.id = id;
        this.frequenceNo = frequenceNo;
        this.name = name;
        this.weeklyOccurrence = stringToOccurences(weeklyOccurrence);
        this.monthlyOccurrence = stringToOccurences(monthlyOccurrence);
    }

    private int[] stringToOccurences(String string)
    {
        String[] parts = string.split("-");
        int[] numbers = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            numbers[i] = Integer.parseInt(parts[i]);
        }

        return numbers;
    }

    public int getFrequenceNo()
    {
        return frequenceNo;
    }
    public String getName()
    {
        return name;
    }
    public int[] getWeeklyOccurrence()
    {
        return weeklyOccurrence;
    }
    public int[] getMonthlyOccurrence()
    {
        return monthlyOccurrence;
    }

    public void changeFrequency(String name, int[] weeklyOccurrence, int[] monthlyOccurrence)
    {
        this.name = name;
        this.weeklyOccurrence = weeklyOccurrence;
        this.monthlyOccurrence = monthlyOccurrence;

    }

    @Override
    public String toString()
    {
        String output;
        output = String.format("%d, %s", frequenceNo,name);
        return output;
    }


}
