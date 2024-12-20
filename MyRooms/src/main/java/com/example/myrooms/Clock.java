package com.example.myrooms;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Clock extends Pane implements Serializable {

    private int  hour, minute, second;
    private int day, month, year;
    private int dayOfWeek;
    public String imagePath = "CsProject-BackGrounds/Clock.png";

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
        this.dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

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
    public int getDayOfWeek(){
        if(dayOfWeek == 1){
            return 6;
        }
        else
            return  dayOfWeek - 2;
    }
    public int getSecond(){

        return second;
    }
//    public int compareTo(int hour, int minute, int second){
//        int difference;
//        int secondResult;
//        int minuteResult;
//        int hourResult;
//
//        //Input 18:40:12
//        //Current 17:32:50
//
//        //Input 18:39:72
//
//
//        if(second - this.second > 0){
//            secondResult = second - this.second;
//        }
//        else{
//            second += 60;
//            minute--;
//            secondResult = second - this.second;
//        }
//
//        if(minute - this.minute > 0){
//            minuteResult = minute - this.minute;
//        }
//        else{
//            minute += 60;
//            hour--;
//            minuteResult = minute - this.minute;
//        }
//
//        hourResult = hour - this.hour;
//
//        difference = hourResult * 3600 + minuteResult * 60 + secondResult;
//
//        return difference;
//    }
    public int getTotalTime(){
        return hour * 3600 + minute * 60 + second;
    }
    public int compareTo(int total){
        return total - getTotalTime();
    }
}
