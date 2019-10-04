/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

import java.util.Calendar;

public class Timer extends PropertyValueObject {
    public static final int SIZE = 9;

    TimerType timerType;
    Calendar date;

    /**
     * @return The timer type.
     */
    public TimerType getTimerType() {
        return timerType;
    }

    /**
     * @return Milliseconds since UNIX Epoch time.
     */
    public Calendar getDate() {
        return date;
    }

    public Timer(TimerType timerType, Calendar date) {
        super(9);
        update(timerType, date);
    }

    public Timer(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public Timer() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 9) throw new CommandParseException();

        int bytePosition = 0;
        timerType = TimerType.fromByte(get(bytePosition));
        bytePosition += 1;

        date = Property.getCalendar(bytes, bytePosition);
    }

    public void update(TimerType timerType, Calendar date) {
        this.timerType = timerType;
        this.date = date;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, timerType.getByte());
        bytePosition += 1;

        set(bytePosition, Property.calendarToBytes(date));
    }

    public void update(Timer value) {
        update(value.timerType, value.date);
    }

    @Override public int getLength() {
        return 1 + 8;
    }

    public enum TimerType implements ByteEnum {
        PREFERRED_START_TIME((byte) 0x00),
        PREFERRED_END_TIME((byte) 0x01),
        DEPARTURE_DATE((byte) 0x02);
    
        public static TimerType fromByte(byte byteValue) throws CommandParseException {
            TimerType[] values = TimerType.values();
    
            for (int i = 0; i < values.length; i++) {
                TimerType state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        TimerType(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}