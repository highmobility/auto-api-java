package com.highmobility.autoapi.property;

/**
 * @deprecated use {@link AutoHvacProperty} instead
 */
@Deprecated
public class AutoHvacState {
    boolean active;
    int day;
    int startHour;
    int startMinute;

    public AutoHvacState(boolean active, int day, int startHour, int startMinute) {
        this.active = active;
        this.day = day;
        this.startHour = startHour;
        this.startMinute = startMinute;
    }

    /**
     * @return The starting hour of the Auto HVAC
     */
    public int getStartHour() {
        return startHour;
    }

    /**
     * @return The starting minute of the Auto HVAC
     */
    public int getStartMinute() {
        return startMinute;
    }

    /**
     * @return Whether the Auto HVAC is active or not
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @return The weekday from 0 - 6
     */
    public int getDay() {
        return day;
    }
}