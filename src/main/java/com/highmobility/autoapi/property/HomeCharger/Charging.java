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

package com.highmobility.autoapi.property.homecharger;

import com.highmobility.autoapi.CommandParseException;

public enum Charging {
    DISCONNECTED((byte) 0x00),
    PLUGGED_IN((byte) 0x01),
    CHARGING((byte) 0x02);

    public static Charging fromByte(byte byteValue) throws CommandParseException {
        Charging[] values = Charging.values();

        for (int i = 0; i < values.length; i++) {
            Charging state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    Charging(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}