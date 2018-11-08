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

package com.highmobility.autoapi.property.value;

import com.highmobility.autoapi.CommandParseException;

public enum Gearbox {
    MANUAL((byte) 0x00),
    AUTOMATIC((byte) 0x01),
    SEMI_AUTOMATIC((byte) 0x02);

    public static Gearbox fromByte(byte value) throws CommandParseException {
        Gearbox[] values = Gearbox.values();

        for (int i = 0; i < values.length; i++) {
            Gearbox value1 = values[i];
            if (value1.getByte() == value) {
                return value1;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    Gearbox(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}