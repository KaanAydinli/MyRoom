package com.example.myrooms;

import java.io.Serializable;
import java.util.ArrayList;

public class Shop implements Serializable {

    public ArrayList<ShopItem> items;
    ShopItem calendar,calendar2,calendar3,calendar4;
    ShopItem alarm1,alarm2,alarm3,alarm4;
    ShopItem clock1,clock2,clock3;
    ShopItem board1,board2;
    ShopItem notebook1,notebook2,notebook3,notebook4;
    ShopItem godfather,loft,cello;
    ShopItem purple,yellow;

    public Shop() {

        items = new ArrayList<>();
        calendar = new ShopItem("Calendar1",true);
        calendar2 = new ShopItem("Calendar2",false);
        calendar3 = new ShopItem("Calendar3",false);
        calendar4 = new ShopItem("Calendar4",false);

        alarm1 = new ShopItem("Alarm1",true);
        alarm2 = new ShopItem("Alarm2",false);
        alarm3 = new ShopItem("Alarm3",false);
        alarm4 = new ShopItem("Alarm4",false);

        clock1 = new ShopItem("Clock1",true);
        clock2 = new ShopItem("Clock2",false);
        clock3 = new ShopItem("Clock3",false);

        board1 = new ShopItem("Board1",true);
        board2 = new ShopItem("Board2",false);

        notebook1 = new ShopItem("Notebook1",true);
        notebook2 = new ShopItem("Notebook2",false);
        notebook3 = new ShopItem("Notebook3",false);
        notebook4 = new ShopItem("Notebook4",false);

        godfather = new ShopItem("Godfather",false);
        loft = new ShopItem("Loft",false);
        cello = new ShopItem("Cello",false);

        purple = new ShopItem("Blue",false);
        yellow = new ShopItem("Green",false);

        items.add(calendar);
        items.add(calendar2);
        items.add(calendar3);
        items.add(calendar4);
        items.add(clock1);
        items.add(clock2);
        items.add(clock3);
        items.add(board1);
        items.add(board2);
        items.add(notebook1);
        items.add(notebook2);
        items.add(notebook3);
        items.add(notebook4);
        items.add(godfather);
        items.add(loft);
        items.add(cello);
        items.add(purple);
        items.add(yellow);
        items.add(alarm1);
        items.add(alarm2);
        items.add(alarm3);
        items.add(alarm4);

    }
    public void setUnlock(String name, boolean unlock) {

        for(ShopItem item : items) {
            if(item.name.equals(name)) {
                item.isUnlocked = unlock;
            }
        }
    }
}
