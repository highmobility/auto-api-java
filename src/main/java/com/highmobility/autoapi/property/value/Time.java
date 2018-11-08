package com.highmobility.autoapi.property.value;

import com.highmobility.value.Bytes;

public class Time extends Bytes {
    int hour;
    int minute;

    public Time(int hour, int minute) {
        this(getInitBytes(hour, minute));
    }

    static byte[] getInitBytes(int hour, int minute) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) hour;
        bytes[1] = (byte) minute;
        return bytes;
    }

    public Time(byte[] bytes) {
        if (bytes.length < 2 || bytes[0] < 0 || bytes[0] > 23 || bytes[1] < 0 || bytes[1] > 59)
            throw new IllegalArgumentException();

        this.hour = bytes[0];
        this.minute = bytes[1];
        this.bytes = bytes;
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

    @Override public boolean equals(Object obj) {
        if (obj instanceof Time == false) return false;
        Time otherTime = (Time) obj;
        return otherTime.getHour() == this.getHour() && otherTime.getMinute() == this.getMinute();
    }
}
