package com.pastimer.desktop;

public class Time {
    private int hour;
    private int minute;
    private int second;

    public Time(){
        hour = 0;
        minute = 25;
        second = 0;
    }

    public Time(int h, int m, int s){
        hour = h;
        minute = m;
        second = s;
    }

    public void changeHour(int c){
        hour = c;
    }

    public void changeMinute(int c){
        minute = c;
    }

    public void changeSecond(int c){
        second = c;
    }

    public int getHour(){
        return hour;
    }

    public int getMinute(){
        return minute;
    }

    public int getSecond(){
        return second;
    }

}
