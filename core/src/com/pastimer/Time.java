package com.pastimer;

public class Time {
    private int hour;
    private int minute;
    private int second;
    private static String zero = "0";

    public Time() {
        hour = 1;
        minute = 1;
        second = 59;
        overBounds();
    }

    public Time(int h, int m, int s) {
        hour = h;
        minute = m;
        second = s;
    }

    public void add() {
        overBounds();

    }

    public void subtract() {
        if (hour == 0 && minute == 0 && second == 0)
            return;
        //subtract second, if its less than 0 set it to 59 --> subtract min if its greater than 0, if min is zero and just got second subtracted and hour greater than 0 subtract hour
        if(second - 1 < 0)
            second = 59;
        else
            second--;
        if(minute == 0 && second == 59 && hour > 0)
            {
                minute = 59;
                hour--;
            }
            else
            if(second == 59)
            minute--;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public String getString(int n) {
        if (n < 10)
            return zero + String.valueOf(n);
        return String.valueOf(n);
    }

    public void overBounds(){
        if (hour >= 24)
            hour = 24;
        if (minute >= 59)
            minute = 59;
        if (second >= 59)
            second = 59;
    }
}
