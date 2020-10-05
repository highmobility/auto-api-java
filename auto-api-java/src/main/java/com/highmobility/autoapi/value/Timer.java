/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

import java.util.Calendar;

import static com.highmobility.utils.ByteUtils.hexFromByte;

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
     * @return Timer date.
     */
    public Calendar getDate() {
        return date;
    }

    public Timer(TimerType timerType, Calendar date) {
        super(0);

        this.timerType = timerType;
        this.date = date;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, timerType.getByte());
        bytePosition += 1;

        set(bytePosition, Property.calendarToBytes(date));
    }

    public Timer(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 9) throw new CommandParseException();

        int bytePosition = 0;
        timerType = TimerType.fromByte(get(bytePosition));
        bytePosition += 1;

        date = Property.getCalendar(bytes, bytePosition);
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
    
            throw new CommandParseException("Timer.TimerType does not contain: " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        TimerType(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}