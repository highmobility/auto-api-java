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

package com.highmobility.autoapi.property.charging;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.PropertyTimestamp;
import com.highmobility.autoapi.property.value.Time;

import java.util.Arrays;
import java.util.Calendar;

import javax.annotation.Nullable;

public class DepartureTime extends Property {
    private static final byte IDENTIFIER = 0x11;

    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public DepartureTime(boolean active, Time time) {
        this(new Value(active, time));
    }

    public DepartureTime(@Nullable Value value, @Nullable Calendar timestamp,
                         @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public DepartureTime(Value value) {
        super(IDENTIFIER, value != null ? 3 : 0);
        this.value = value;

        if (value != null) {
            bytes[3] = Property.boolToByte(value.active);
            bytes[4] = (byte) value.time.getHour();
            bytes[5] = (byte) value.time.getMinute();
        }
    }

    public DepartureTime(Property p) throws CommandParseException {
        super(p);
        update(p, null, null, false);
    }

    @Override
    public boolean update(Property p, PropertyFailure failure, PropertyTimestamp timestamp,
                          boolean propertyInArray) throws CommandParseException {
        if (p != null) value = new Value(p);
        return super.update(p, failure, timestamp, propertyInArray);
    }

    public static class Value {
        boolean active;
        Time time;

        /**
         * @return The activation state.
         */
        public boolean isActive() {
            return active;
        }

        /**
         * @return The departure time.
         */
        public Time getTime() {
            return time;
        }

        public Value(boolean active, Time time) {
            this.active = active;
            this.time = time;
        }

        public Value(Property bytes) {
            active = Property.getBool(bytes.get(3));
            time = new Time(Arrays.copyOfRange(bytes.getByteArray(), 4, 6));
        }

    }
}
