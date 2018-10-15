package com.highmobility.autoapi.value;

/**
 * Local time with hours and minutes.
 */
public class Time {
    int hour;
    int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public Time(byte[] bytes) {
        if (bytes.length < 2 || bytes[0] < 0 || bytes[0] > 23 || bytes[1] < 0 || bytes[0] > 59)
            throw new IllegalArgumentException();
        this.hour = bytes[0];
        this.minute = bytes[1];
    }

    /**
     * @return Hour from 0 - 23.
     */
    public int getHour() {
        return hour;
    }

    /**
     * @return Minute from 0 - 59.
     */
    public int getMinute() {
        return minute;
    }
}
