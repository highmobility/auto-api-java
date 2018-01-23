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

public class ClimateProfileProperty extends Property {
    public ClimateProfileProperty(byte identifier, AutoHvacState[] states) throws IllegalArgumentException {
        super(identifier, 15);

        if (states.length != 7) throw new IllegalArgumentException("Less than 7 auto hvac states");

        byte autoHvacDatesByte = 0;
        
        for (int i = 0; i < 7; i++) {
            if (states[i].isActive()) {
                autoHvacDatesByte = (byte) (autoHvacDatesByte | (1 << i));
            }

            bytes[4 + i * 2] = (byte)states[i].getStartHour();
            bytes[4 + i * 2 + 1] = (byte)states[i].getStartMinute();
        }

        bytes[3] = autoHvacDatesByte;
    }
}
