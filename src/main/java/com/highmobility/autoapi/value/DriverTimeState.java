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

import static com.highmobility.utils.ByteUtils.hexFromByte;

public class DriverTimeState extends PropertyValueObject {
    public static final int SIZE = 2;

    Integer driverNumber;
    TimeState timeState;

    /**
     * @return The driver number.
     */
    public Integer getDriverNumber() {
        return driverNumber;
    }

    /**
     * @return The time state.
     */
    public TimeState getTimeState() {
        return timeState;
    }

    public DriverTimeState(Integer driverNumber, TimeState timeState) {
        super(0);

        this.driverNumber = driverNumber;
        this.timeState = timeState;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(driverNumber, 1));
        bytePosition += 1;

        set(bytePosition, timeState.getByte());
    }

    public DriverTimeState(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        driverNumber = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        timeState = TimeState.fromByte(get(bytePosition));
    }

    @Override public int getLength() {
        return 1 + 1;
    }

    public enum TimeState implements ByteEnum {
        NORMAL((byte) 0x00),
        FIFTEEN_MIN_BEFORE_FOUR((byte) 0x01),
        FOUR_REACHED((byte) 0x02),
        FIFTEEN_MIN_BEFORE_NINE((byte) 0x03),
        NINE_REACHED((byte) 0x04),
        FIFTEEN_MIN_BEFORE_SIXTEEN((byte) 0x05),
        SIXTEEN_REACHED((byte) 0x06);
    
        public static TimeState fromByte(byte byteValue) throws CommandParseException {
            TimeState[] values = TimeState.values();
    
            for (int i = 0; i < values.length; i++) {
                TimeState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("DriverTimeState.TimeState does not contain: " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        TimeState(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}