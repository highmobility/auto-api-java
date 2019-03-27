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
import com.highmobility.autoapi.property.ByteEnum;

public enum GearMode implements ByteEnum {
    MANUAL((byte) 0x00),
    PARK((byte) 0x01),
    REVERSE((byte) 0x02),
    NEUTRAL((byte) 0x03),
    DRIVE((byte) 0x04),
    LOW_GEAR((byte) 0x05),
    SPORT((byte) 0x06);

    public static GearMode fromByte(byte byteValue) throws CommandParseException {
        GearMode[] values = GearMode.values();

        for (int i = 0; i < values.length; i++) {
            GearMode state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    GearMode(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}