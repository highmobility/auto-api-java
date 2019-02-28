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

package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public class DriverWorkingState extends Property {
    public static final byte IDENTIFIER = 0x01;

    int driverNumber;
    WorkingState workingState;

    public int getDriverNumber() {
        return driverNumber;
    }

    public WorkingState getWorkingState() {
        return workingState;
    }

    public DriverWorkingState(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 8) throw new CommandParseException();
        driverNumber = Property.getUnsignedInt(bytes[6]);
        workingState = WorkingState.fromByte(bytes[7]);
    }

    public DriverWorkingState(int driverNumber, WorkingState workingState) throws IllegalArgumentException {
        this(IDENTIFIER, driverNumber, workingState);
    }

    public DriverWorkingState(byte identifier, int driverNumber, WorkingState workingState) throws
            IllegalArgumentException {
        super(identifier, 2);

        bytes[6] = (byte) driverNumber;
        bytes[7] = workingState.getByte();
    }

    public enum WorkingState {
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

        public byte getByte() {
            return value;
        }
    }
}