package com.example.myrooms;
import com.habittracker.HabitTracker;

public class User
{
    private HabitTracker tracker;
    private int id;

    public User(int id)
    {
        this.id = id;
        tracker = new HabitTracker(id);
    }
    public HabitTracker getHabitTracker()
    {
        return tracker;
    }

}
