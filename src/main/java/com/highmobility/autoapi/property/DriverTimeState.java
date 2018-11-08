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

public class DriverTimeState extends Property {
    public static final byte IDENTIFIER = 0x02;

    int driverNumber;
    TimeState timeState;

    public int getDriverNumber() {
        return driverNumber;
    }

    public TimeState getTimeState() {
        return timeState;
    }

    public DriverTimeState(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 5) throw new CommandParseException();
        driverNumber = Property.getUnsignedInt(bytes[3]);
        timeState = TimeState.fromByte(bytes[4]);
    }

    public DriverTimeState(int driverNumber, TimeState timeState) throws
            IllegalArgumentException {
        this(IDENTIFIER, driverNumber, timeState);
    }

    public DriverTimeState(byte identifier, int driverNumber, TimeState timeState) throws
            IllegalArgumentException {
        super(identifier, 2);

        bytes[3] = (byte) driverNumber;
        bytes[4] = timeState.getByte();
    }

    public enum TimeState {
        NORMAL((byte) 0x00),
        FIFTEEN_MINUTES_BEFORE_FOUR_AND_HALF_HOURS((byte) 0x01),
        FOUR_AND_HALF_HOURS_REACHED((byte) 0x02),
        FIFTEEN_MINUTES_BEFORE_NINE_HOURS((byte) 0x03),
        NINE_HOURS_REACHED((byte) 0x04),
        FIFTEEN_MINUTES_BEFORE_SIXTEEN_HOURS((byte) 0x05),
        SIXTEEN_HOURS_REACHED((byte) 0x06);

        public static TimeState fromByte(byte byteValue) throws CommandParseException {
            TimeState[] values = TimeState.values();

            for (int i = 0; i < values.length; i++) {
                TimeState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        TimeState(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }

}