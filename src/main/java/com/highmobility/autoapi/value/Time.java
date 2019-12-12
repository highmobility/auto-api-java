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

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class Time extends PropertyValueObject {
    public static final int SIZE = 2;

    Integer hour;
    Integer minute;

    /**
     * @return Value between 0 and 23.
     */
    public Integer getHour() {
        return hour;
    }

    /**
     * @return Value between 0 and 59.
     */
    public Integer getMinute() {
        return minute;
    }

    public Time(Integer hour, Integer minute) {
        super(2);
        update(hour, minute);
    }

    public Time(Property property) throws CommandParseException {
        super();
        if (property.getValueComponent() == null) throw new CommandParseException();
        update(property.getValueComponent().getValueBytes());
    }

    public Time() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        hour = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        minute = Property.getUnsignedInt(bytes, bytePosition, 1);
    }

    public void update(Integer hour, Integer minute) {
        this.hour = hour;
        this.minute = minute;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(hour, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(minute, 1));
    }

    public void update(Time value) {
        update(value.hour, value.minute);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}