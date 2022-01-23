package com.pastimer;

public class Time {
    private int minute;
    private int second;
    private static String zero = "0";

    public Time() {
        minute = 0;
        second = 0;
        overBounds();
    }

    public Time(int m, int s) {
        minute = m;
        second = s;
        overBounds();
    }

    public void subtract() {
        if (minute == 0 && second == 0)
            return;
        if (second - 1 < 0)
            second = 59;
        else
            second--;
        if(second == 59 && minute > 0)
            minute--;
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

    public void overBounds() {
        if (minute > 59){
            minute = 59;
            if(second > 59)
                second = 59;
        }
        else
            if (second > 59)
            second = 59;
    }
}
