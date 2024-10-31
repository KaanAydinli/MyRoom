package com.example.myrooms;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Clock extends Pane {

    private int  hour, minute, second;
    private int day, month, year;

    public Clock(){

        setCurrent();
    }
    public void setCurrent(){
        Calendar calendar = new GregorianCalendar();

        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);
        this.second = calendar.get(Calendar.SECOND);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = calendar.get(Calendar.MONTH);
        this.year = calendar.get(Calendar.YEAR);

        writeClock();
    }
    public void writeClock(){


        double radius = Math.min(getWidth(),getHeight()) * 0.4;
        double coorX = getWidth() / 2;
        double coorY = getHeight() / 2;



        double sl = radius * 0.65;
        double ml = radius * 0.5;
        double hl = radius * 0.35;

        double sx = coorX + sl * Math.sin(second * (2 * Math.PI / 60));
        double mx = coorX + ml * Math.sin(minute * (2 * Math.PI / 60));
        double hx = coorX + hl * Math.sin((hour % 12 + minute / 60) * (2 * Math.PI / 12));

        double sy = coorY - sl * Math.cos(second * (2 * Math.PI / 60));
        double my = coorY - ml * Math.cos(minute * (2 * Math.PI / 60));
        double hy = coorY - hl * Math.cos((hour % 12 + minute / 60) * (2 * Math.PI / 12));



        Line sli = new Line(coorX, coorY, sx, sy);
        Line mli = new Line(coorX, coorY, mx, my);
        Line hli = new Line(coorX, coorY, hx, hy);

        sli.setStroke(Color.DARKRED);
        mli.setStroke(Color.BLACK);
        hli.setStroke(Color.BLACK);

        sli.setStrokeWidth(1);
        mli.setStrokeWidth(2);
        hli.setStrokeWidth(2);

        getChildren().clear();
        getChildren().addAll(sli,mli,hli);

    }
    public void setWidth(double width){
        super.setWidth(width);
        writeClock();
    }
    public void setHeight(double height){
        super.setHeight(height);
        writeClock();
    }
    public String getTime(){
        return hour + ":" + minute + ":" + second;
    }
    public String getDate(){
        return day + "." + month + "." + year;
    }
    public int getDay(){
        return day;
    }
    public int getHour(){
        return hour;
    }
    public int getMinute(){
        return minute;
    }
    public Month getMonth(){
        return Month.of(month + 1);
    }
}
