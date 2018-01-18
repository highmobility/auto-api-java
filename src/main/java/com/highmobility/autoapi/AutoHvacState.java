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

package com.highmobility.autoapi;

/**
 * Created by ttiganik on 20/12/2016.
 */
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
     *
     * @return The starting hour of the auto HVAC
     */
    public int getStartHour() {
        return startHour;
    }

    /**
     *
     * @return The starting minute of the auto HVAC
     */
    public int getStartMinute() {
        return startMinute;
    }

    /**
     *
     * @return Whether the auto HVAC is active or not
     */
    public boolean isActive() {
        return active;
    }

    /**
     *
     * @return The weekday from 0 - 6
     */
    public int getDay() {
        return day;
    }
}