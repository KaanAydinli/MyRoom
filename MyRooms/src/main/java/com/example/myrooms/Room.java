package com.example.myrooms;

import java.io.Serializable;

public class Room implements Serializable {

    Alarm alarm;
    Clock clock1;
    Clock clock2;
    BookCase bookcase;
    Board board;

    public Room(Alarm alarm,Clock clock1, Clock clock2, BookCase bookcase, Board board){
        this.alarm = alarm;
        this.clock1 = clock1;
        this.clock2 = clock2;
        this.bookcase = bookcase;
        this.board = board;
    }
}
