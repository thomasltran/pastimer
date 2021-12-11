package com.pastimer;

public class Time {
    private int hour;
    private int minute;
    private int second;
    private static String zero = "0";

    public Time(){
        hour = 69;
        minute = 42;
        second = 1;
    }

    public Time(int h, int m, int s){
        hour = h;
        minute = m;
        second = s;
    }

    public void subtract(){
        second--;
        if(second == 0 && minute > 0){
            minute--;
            second = 59;
        }
        else
            if(minute == 0){
                hour--;
                minute = 59;
            }
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

    public String getString(int n){
        if(n < 10)
            return zero+String.valueOf(n);
        return String.valueOf(n);
    }

}
