/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

/**
 * AutoHvacProperty expects 7 weekdayStates for 7 days, even if its not active on some of the days.
 */
public class AutoHvacProperty extends Property {
    WeekdayState[] weekdayWeekdayStates;
    boolean isConstant;

    /**
     * Return a Auto HVAC state for a single day.
     *
     * @param day The weekday, starting from 0.
     * @return The Auto HVAC state.
     */
    public WeekdayState getState(int day) {
        for (int i = 0; i < weekdayWeekdayStates.length; i++) {
            WeekdayState weekdayWeekdayState = weekdayWeekdayStates[i];
            if (weekdayWeekdayState.day == day) return weekdayWeekdayState;
        }

        return null;
    }

    /**
     * @return The Auto HVAC states for all of the weekdays.
     */
    public WeekdayState[] getStates() {
        return weekdayWeekdayStates;
    }

    /**
     * @return Indication on whether Auto HVAC is constant - based on the car surroundings.
     */
    public boolean isConstant() {
        return isConstant;
    }

    public AutoHvacProperty(WeekdayState[] weekdayWeekdayStates, boolean isConstant) {
        super((byte) 0x00, 15);

        if (weekdayWeekdayStates.length != 7) throw new IllegalArgumentException("Less than 7 auto hvac weekdayStates");

        byte autoHvacDatesByte = 0;
        if (isConstant) autoHvacDatesByte = (byte) (autoHvacDatesByte | (1 << 7)); // TODO: test

        for (int i = 0; i < 7; i++) {
            if (weekdayWeekdayStates[i].isActive()) {
                autoHvacDatesByte = (byte) (autoHvacDatesByte | (1 << i));
            }

            bytes[4 + i * 2] = (byte) weekdayWeekdayStates[i].getStartHour();
            bytes[4 + i * 2 + 1] = (byte) weekdayWeekdayStates[i].getStartMinute();
        }

        bytes[3] = autoHvacDatesByte;

        this.weekdayWeekdayStates = weekdayWeekdayStates;
    }

    public AutoHvacProperty(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length != 18) throw new CommandParseException();
        int hvacActiveOnDays = bytes[3];
        isConstant = Property.getBit(hvacActiveOnDays, 7);
        weekdayWeekdayStates = new WeekdayState[7];

        for (int j = 0; j < 7; j++) {
            boolean active = Property.getBit(hvacActiveOnDays, j);
            int hour = bytes[4 + j * 2];
            int minute = bytes[4 + j * 2 + 1];
            weekdayWeekdayStates[j] = new WeekdayState(active, j, hour, minute);
        }
    }

    public static class WeekdayState {
        boolean active;
        int day;
        int startHour;
        int startMinute;

        public WeekdayState(boolean active, int day, int startHour, int startMinute) {
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
}