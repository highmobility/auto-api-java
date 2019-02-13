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
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

import java.util.Calendar;

/**
 * The charging timer.
 */
public class ChargingTimer extends PropertyValueObject {
    Type type;
    Calendar time;

    public Type getType() {
        return type;
    }

    public Calendar getTime() {
        return time;
    }

    public ChargingTimer() {
    }

    public ChargingTimer(Type type, Calendar time) {
        super(9);

        set(0, type.getByte());
        set(1, Property.calendarToBytes(time));

        this.type = type;
        this.time = time;
    }

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        type = Type.fromByte(get(0));
        time = Property.getCalendar(bytes, 1);
    }

    public enum Type {
        PREFERRED_START_TIME((byte) 0x00),
        PREFERRED_END_TIME((byte) 0x01),
        DEPARTURE_TIME((byte) 0x02);

        public static Type fromByte(byte byteCharginTimer) throws CommandParseException {
            Type[] values = Type.values();

            for (int i = 0; i < values.length; i++) {
                Type state = values[i];
                if (state.getByte() == byteCharginTimer) {
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
