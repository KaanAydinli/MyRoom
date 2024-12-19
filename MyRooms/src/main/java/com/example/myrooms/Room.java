package com.example.myrooms;

import java.io.Serializable;

public class Room implements Serializable {

    Alarm alarm;
    Clock clock1;
    Clock clock2;
    BookCase bookcase;
    Board board;
    Plant plant;
    Notebooks notebooks;
    Calendars calendars;
    Shop shop;
    Settings settings;
    MusicPlayer musicPlayer;
    Charts charts;

    public Room(Alarm alarm,Clock clock1, Clock clock2, BookCase bookcase, Board board,Plant plant, Notebooks notebooks, Calendars calendars,Shop shop, Settings settings,MusicPlayer musicPlayer, Charts charts) {
        this.alarm = alarm;
        this.clock1 = clock1;
        this.clock2 = clock2;
        this.bookcase = bookcase;
        this.board = board;
        this.plant = plant;
        this.notebooks = notebooks;
        this.calendars = calendars;
        this.shop = shop;
        this.settings = settings;
        this.musicPlayer = musicPlayer;
        this.charts = charts;
    }
}
