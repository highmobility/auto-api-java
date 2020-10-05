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

public class DriverWorkingState extends PropertyValueObject {
    public static final int SIZE = 2;

    Integer driverNumber;
    WorkingState workingState;

    /**
     * @return The driver number.
     */
    public Integer getDriverNumber() {
        return driverNumber;
    }

    /**
     * @return The working state.
     */
    public WorkingState getWorkingState() {
        return workingState;
    }

    public DriverWorkingState(Integer driverNumber, WorkingState workingState) {
        super(0);

        this.driverNumber = driverNumber;
        this.workingState = workingState;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(driverNumber, 1));
        bytePosition += 1;

        set(bytePosition, workingState.getByte());
    }

    public DriverWorkingState(Bytes valueBytes) throws CommandParseException {
        super(valueBytes);

        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        driverNumber = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        workingState = WorkingState.fromByte(get(bytePosition));
    }

    @Override public int getLength() {
        return 1 + 1;
    }

    public enum WorkingState implements ByteEnum {
        RESTING((byte) 0x00),
        DRIVER_AVAILABLE((byte) 0x01),
        WORKING((byte) 0x02),
        DRIVING((byte) 0x03);
    
        public static WorkingState fromByte(byte byteValue) throws CommandParseException {
            WorkingState[] values = WorkingState.values();
    
            for (int i = 0; i < values.length; i++) {
                WorkingState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("DriverWorkingState.WorkingState does not contain: " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        WorkingState(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}