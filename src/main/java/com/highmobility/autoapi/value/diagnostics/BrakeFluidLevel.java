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

package com.highmobility.autoapi.value.diagnostics;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.ByteEnum;

public enum BrakeFluidLevel implements ByteEnum {
    LOW((byte) 0x00),
    FULL((byte) 0x01);

    public static BrakeFluidLevel fromByte(byte byteValue) throws CommandParseException {
        BrakeFluidLevel[] values = BrakeFluidLevel.values();

        for (int i = 0; i < values.length; i++) {
            BrakeFluidLevel state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    BrakeFluidLevel(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }


}
