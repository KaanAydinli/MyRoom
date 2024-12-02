package com.example.myrooms;


public class Alarm {

    int second;
    int minute;
    int hour;
    int mode; //Normal for 0 and Pomodoro for 1
    boolean isOn;
    String imagePath;

    public Alarm(){
        mode = 0;
        isOn = false;
    }
    public void createAlarmNormal(int hour,int minute,int second){

        this.hour = hour;
        this.minute = minute;
        this.second = second;
        isOn = true;
    }
    public void createAlarmPomodoro(int session, int breakTime){

    }
    public boolean checkAlarm(int hour,int minute,int second){
        return isOn && hour == this.hour && minute == this.minute && second == this.second;
    }
    public void deactivateAlarm(){
        isOn = false;
    }
    public void setimagePath(String imagePath){
        this.imagePath = imagePath;
    }
    public String getimagePath(){
        return imagePath;
    }
    public String getAlarmTime(){
        return hour+":"+minute+":"+second;
    }
}
