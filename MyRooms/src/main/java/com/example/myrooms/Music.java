package com.example.myrooms;
import java.io.Serializable;

public class Music implements Serializable {

    private  String name;
    private boolean locked;
    private int idOfMusic;

    public Music(int id)
    {
        this.idOfMusic = id;
        this.locked = false;
    }

    public void unlock()
    {
        this.locked = false;
    }

    public void lock()
    {
        this.locked = true;
    }

    public String getName()
    {
        return this.name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public boolean getLockSituation()
    {
        return this.locked;
    }

    public int getId()
    {
        return this.idOfMusic;

    }

}




