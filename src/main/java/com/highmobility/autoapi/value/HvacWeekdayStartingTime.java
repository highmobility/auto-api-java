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

public class HvacWeekdayStartingTime extends PropertyValueObject {
    public static final int SIZE = 3;

    Weekday weekday;
    Time time;

    /**
     * @return The weekday.
     */
    public Weekday getWeekday() {
        return weekday;
    }

    /**
     * @return The time.
     */
    public Time getTime() {
        return time;
    }

    public HvacWeekdayStartingTime(Weekday weekday, Time time) {
        super(3);
        update(weekday, time);
    }

    public HvacWeekdayStartingTime(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public HvacWeekdayStartingTime() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 3) throw new CommandParseException();

        int bytePosition = 0;
        weekday = Weekday.fromByte(get(bytePosition));
        bytePosition += 1;

        int timeSize = Time.SIZE;
        time = new Time();
        time.update(getRange(bytePosition, bytePosition + timeSize));
    }

    public void update(Weekday weekday, Time time) {
        this.weekday = weekday;
        this.time = time;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, weekday.getByte());
        bytePosition += 1;

        set(bytePosition, time);
    }

    public void update(HvacWeekdayStartingTime value) {
        update(value.weekday, value.time);
    }

    @Override public int getLength() {
        return 1 + 2;
    }

    public enum Weekday implements ByteEnum {
        MONDAY((byte) 0x00),
        TUESDAY((byte) 0x01),
        WEDNESDAY((byte) 0x02),
        THURSDAY((byte) 0x03),
        FRIDAY((byte) 0x04),
        SATURDAY((byte) 0x05),
        SUNDAY((byte) 0x06),
        AUTOMATIC((byte) 0x07);
    
        public static Weekday fromByte(byte byteValue) throws CommandParseException {
            Weekday[] values = Weekday.values();
    
            for (int i = 0; i < values.length; i++) {
                Weekday state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Weekday(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}