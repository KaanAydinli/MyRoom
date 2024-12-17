package com.example.myrooms;

import java.io.Serializable;

public class Watercan implements Serializable {
    private  final int watercanMaxLevel  = 3;
    public String imagePath = "CsProject-BackGrounds/watercanempty.png";
    private int waterLevel;

    public Watercan(int waterLevel) {

        this.waterLevel = waterLevel;
    }

    public int getWatercanLevel() {
        return watercanMaxLevel;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }
}
