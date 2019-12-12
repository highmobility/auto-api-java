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
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

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
        super(2);
        update(driverNumber, workingState);
    }

    public DriverWorkingState(Property property) throws CommandParseException {
        super();
        if (property.getValueComponent() == null) throw new CommandParseException();
        update(property.getValueComponent().getValueBytes());
    }

    public DriverWorkingState() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        driverNumber = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        workingState = WorkingState.fromByte(get(bytePosition));
    }

    public void update(Integer driverNumber, WorkingState workingState) {
        this.driverNumber = driverNumber;
        this.workingState = workingState;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(driverNumber, 1));
        bytePosition += 1;

        set(bytePosition, workingState.getByte());
    }

    public void update(DriverWorkingState value) {
        update(value.driverNumber, value.workingState);
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
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        WorkingState(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}