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

package com.highmobility.autoapi.property.tachograph;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class DriverWorkingState extends PropertyValueObject {
    int driverNumber;
    Value workingState;

    public int getDriverNumber() {
        return driverNumber;
    }

    public Value getWorkingState() {
        return workingState;
    }

    public DriverWorkingState(int driverNumber, Value workingState) {
        super(2);
        update(driverNumber, workingState);
    }

    public DriverWorkingState() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();
        driverNumber = get(0);
        workingState = Value.fromByte(get(1));
    }

    public void update(int driverNumber, Value workingState) {
        this.driverNumber = driverNumber;
        this.workingState = workingState;
        bytes = new byte[2];

        set(0, (byte) driverNumber);
        set(1, workingState.getByte());
    }

    public void update(DriverWorkingState value) {
        update(value.driverNumber, value.workingState);
    }

    public enum Value {
        RESTING((byte) 0x00),
        DRIVER_AVAILABLE((byte) 0x01),
        WORKING((byte) 0x02),
        DRIVING((byte) 0x03);

        public static Value fromByte(byte byteValue) throws CommandParseException {
            Value[] values = Value.values();

            for (int i = 0; i < values.length; i++) {
                Value state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Value(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}