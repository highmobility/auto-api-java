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

package com.highmobility.autoapi.value.lights;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.ByteEnum;

public enum FrontExteriorLightState implements ByteEnum {
    INACTIVE((byte) 0x00),
    ACTIVE((byte) 0x01),
    ACTIVE_FULL_BEAM((byte) 0x02),
    ACTIVE_DAYLIGHT_RUNNING_LAMPS((byte) 0x03),
    AUTOMATIC((byte) 0x04);

    public static FrontExteriorLightState fromByte(byte value) throws CommandParseException {
        FrontExteriorLightState[] values = FrontExteriorLightState.values();

        for (int i = 0; i < values.length; i++) {
            FrontExteriorLightState capability = values[i];
            if (capability.getByte() == value) {
                return capability;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    FrontExteriorLightState(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }


}