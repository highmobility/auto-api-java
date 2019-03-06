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

package com.highmobility.autoapi.property.maintenance;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.PropertyValueSingleByte;

public enum TeleserviceAvailability implements PropertyValueSingleByte {
    PENDING((byte)0x00),
    IDLE((byte)0x01),
    SUCCESSFUL((byte)0x02),
    ERROR((byte)0x03);

    public static TeleserviceAvailability fromByte(byte byteValue) throws CommandParseException {
        TeleserviceAvailability[] values = TeleserviceAvailability.values();

        for (int i = 0; i < values.length; i++) {
            TeleserviceAvailability state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    TeleserviceAvailability(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}
