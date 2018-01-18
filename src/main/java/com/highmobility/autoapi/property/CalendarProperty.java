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

import java.util.Calendar;

public class CalendarProperty extends Property {
    public  CalendarProperty(byte identifier, Calendar cal) {
        super(identifier, 8);

        bytes[3] = (byte) (cal.get(Calendar.YEAR) - 2000);
        bytes[4] = (byte) (cal.get(Calendar.MONTH) + 1);
        bytes[5] = (byte) cal.get(Calendar.DAY_OF_MONTH);
        bytes[6] = (byte) cal.get(Calendar.HOUR_OF_DAY);
        bytes[7] = (byte) cal.get(Calendar.MINUTE);
        bytes[8] = (byte) cal.get(Calendar.SECOND);

        int msOffset = cal.getTimeZone().getRawOffset(); // in ms
        int minuteOffset = msOffset / (60 * 1000);

        byte[] bytesOffset = Property.intToBytes(minuteOffset, 2);
        bytes[9] = bytesOffset[0];
        bytes[10] = bytesOffset[1];
    }
}
