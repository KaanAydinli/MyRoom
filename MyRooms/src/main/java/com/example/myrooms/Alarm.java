package com.example.myrooms;

public class Alarm {

    int second;
    int minute;
    int hour;
    int mode; //Normal for 0 and Pomodoro for 1
    boolean isOn;
    String imagePath;
    Clock clock;
    int sessionTime;
    int breakTime;

    public Alarm(Clock clock){
        mode = 1;
        isOn = false;
        this.clock = clock;
    }
    public void createAlarmNormal(int hour,int minute,int second){

        mode = 0;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        isOn = true;
    }
    public void createAlarmPomodoro(int session, int breakTime){
        mode = 1;
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
}
