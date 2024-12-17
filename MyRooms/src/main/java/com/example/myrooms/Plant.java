package com.example.myrooms;

import java.io.Serializable;

public class Plant implements Serializable {
    public String imagePath = "CsProject-BackGrounds/plantlevel1.png";
    private int levelOfPlant;
    Watercan watercan;
    public Plant(int levelOfPlant, Watercan watercan) {
        this.levelOfPlant = levelOfPlant;
        this.watercan = watercan;
    }

    public void setLevelOfPlant(int levelOfPlant) {
        this.levelOfPlant = levelOfPlant;

    }
    public int getLevelOfPlant() {
        return levelOfPlant;
    }
}
