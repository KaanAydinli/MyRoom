package com.example.myrooms;

public class Alarm {

    private int second;
    private int minute;
    private int hour;
    int mode; //Normal for 0 and Pomodoro for 1
    boolean isOn;
    String imagePath;
    Clock clock;
    int sessionTime;
    int breakTime;
    private int startingTime;

    public Alarm(Clock clock){
        mode = 0;
        isOn = false;
        this.clock = clock;
    }
    public void createAlarmNormal(int hour,int minute,int second){

        this.hour = hour;
        this.minute = minute;
        this.second = second;
        isOn = true;
    }
    public void createAlarmPomodoro(int session, int breakTime){

        isOn = true;

        sessionTime = session;
        this.breakTime = breakTime;

        minute = clock.getMinute() + sessionTime;

        if(minute > 60){
            minute = minute - 60;
            hour = clock.getHour() + 1;
        }
        else{
            hour = clock.getHour();
        }
    }
    public boolean checkAlarm(int hour,int minute,int second){

        if(mode == 0){

            return isOn && hour == this.hour && minute == this.minute && second == this.second;
        }
        else{
            if(isOn && hour == this.hour && minute == this.minute && second == this.second){

                createAlarmPomodoro(this.breakTime,this.sessionTime) ;
                return true;
            }
            return false;
        }
    }
    public void setStartingTime(int startingTime){
        this.startingTime = startingTime;
    }
    public int getStartingTime(){
        return startingTime;
    }
    public void deactivateAlarm(){
        isOn = true;
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
    public int getSecond(){
        return second;
    }
    public int getMinute(){
        return minute;
    }
    public int getHour(){
        return hour;
    }
    public int getTotalTime(){
        return hour * 3600 + minute * 60 + second;
    }
    public int compareTo(int total){
        return getTotalTime() - total;
    }
}
