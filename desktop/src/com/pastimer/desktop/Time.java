package com.pastimer.desktop;

public class Time {
    private int hour;
    private int minute;
    private int second;

    public Time(){
        this.hour = 0;
        this.minute = 25;
        this.second = 0;
    }

    public Time(int h, int m, int s){
        this.hour = h;
        this.minute = m;
        this.second = s;
    }

    public void changeHour(int c){
        this.hour = c;
    }

    public void changeMinute(int c){
        this.hour = c;
    }

    public void changeSecond(int c){
        this.hour = c;
    }

}
