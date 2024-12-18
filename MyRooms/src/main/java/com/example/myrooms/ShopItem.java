package com.example.myrooms;

import java.io.Serializable;

public class ShopItem implements Serializable {

    String name;
    boolean isUnlocked;

    public ShopItem(String name, boolean unlocked) {
        this.name = name;
        this.isUnlocked = unlocked;
    }
}
