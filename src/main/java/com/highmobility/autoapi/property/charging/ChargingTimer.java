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
import com.highmobility.utils.ByteUtils;

import java.util.Calendar;

import javax.annotation.Nullable;

/**
 * The charging timer.
 */
public class ChargingTimer extends Property {
    Value value;

    @Nullable public Value getValue() {
        return value;
    }

    public ChargingTimer(byte identifier) {
        super(identifier);
    }

    public ChargingTimer(Value.Type type, Calendar calendar) {
        this(new Value(type, calendar));
    }

    public ChargingTimer(Value value) {
        super((byte) 0x00, value != null ? 9 : 0);
        this.value = value;

        if (value != null) {
            bytes[3] = value.type.getByte();
            ByteUtils.setBytes(bytes, Property.calendarToBytes(value.time), 4);
        }
    }

    public ChargingTimer(@Nullable Value value, @Nullable Calendar timestamp,
                         @Nullable PropertyFailure failure) {
        this(value);
        setTimestampFailure(timestamp, failure);
    }

    public ChargingTimer(Property p) throws CommandParseException {
        super(p);
        update(p, null, null, false);
    }

    @Override
    public boolean update(Property p, PropertyFailure failure, PropertyTimestamp timestamp,
                          boolean propertyInArray) throws CommandParseException {
        if (p != null) {
            if (bytes.length < 12) throw new CommandParseException();
            value = new Value(bytes);
        }
        return super.update(p, failure, timestamp, propertyInArray);
    }

    public static class Value {
        Type type;
        Calendar time;

        public Type getType() {
            return type;
        }

        public Calendar getTime() {
            return time;
        }

        public Value(byte[] bytes) throws CommandParseException {
            type = Type.fromByte(bytes[3]);
            time = Property.getCalendar(bytes, 4);
        }

        public Value(Type type, Calendar time) {
            this.type = type;
            this.time = time;
        }

        public enum Type {
            PREFERRED_START_TIME((byte) 0x00),
            PREFERRED_END_TIME((byte) 0x01),
            DEPARTURE_TIME((byte) 0x02);

            public static Type fromByte(byte byteValue) throws CommandParseException {
                Type[] values = Type.values();

                for (int i = 0; i < values.length; i++) {
                    Type state = values[i];
                    if (state.getByte() == byteValue) {
                        return state;
                    }
                }

                throw new CommandParseException();
            }

            private byte value;

            Type(byte value) {
                this.value = value;
            }

            public byte getByte() {
                return value;
            }
        }
    }
}