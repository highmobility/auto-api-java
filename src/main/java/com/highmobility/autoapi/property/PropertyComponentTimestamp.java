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

import com.highmobility.value.Bytes;

import java.util.Calendar;

public class PropertyComponentTimestamp extends PropertyComponent {
    private static final byte IDENTIFIER = 0x02;
    private Calendar timestamp;

    /**
     * @return The timestamp calendar.
     */
    public Calendar getCalendar() {
        return timestamp;
    }

    public PropertyComponentTimestamp(Bytes bytes) {
        super(bytes);
        if (getLength() < 11) throw new IllegalArgumentException("Invalid timestamp length");
        timestamp = Property.getCalendar(this, 3);

    }

    public PropertyComponentTimestamp(Calendar timestamp) {
        super(IDENTIFIER, PropertyComponentValue.CALENDAR_SIZE);
        this.timestamp = timestamp;
        valueBytes = new Bytes(Property.calendarToBytes(timestamp));
        set(3, valueBytes);
    }
}
