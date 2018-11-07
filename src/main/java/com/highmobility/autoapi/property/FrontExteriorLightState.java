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

public enum FrontExteriorLightState {
    INACTIVE((byte) (0x00)),
    ACTIVE((byte) 0x01),
    ACTIVE_WITH_FULL_BEAM((byte) 0x02);

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