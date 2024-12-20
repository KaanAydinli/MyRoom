package com.example.myrooms;

import java.io.Serializable;

public class User implements Serializable {

    public String name;
    public String password;
    public Integer totalCoin;
    public Integer totalHours;
    public Room room;
    public Integer ID;

    public User(String name, String password, int totalCoin, int totalHours, Room room, Integer ID) {

        this.name = name;
        this.password = password;
        this.totalCoin = totalCoin;
        this.totalHours = totalHours;
        this.room = room;
        this.ID = ID;

    }

}
