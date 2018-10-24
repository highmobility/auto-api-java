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

package com.highmobility.autoapi.property.diagnostics;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.value.Location;
import com.highmobility.utils.ByteUtils;

public class TirePressure extends Property {
    Location location;
    float pressure;

    /**
     * @return The tire location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The tire pressure.
     */
    public float getPressure() {
        return pressure;
    }

    public TirePressure(Location location, float pressure) {
        super((byte) 0x00, 5);
        this.location = location;
        this.pressure = pressure;
        bytes[3] = location.getByte();
        ByteUtils.setBytes(bytes, Property.floatToBytes(pressure), 4);
    }

    public TirePressure(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 8) throw new CommandParseException();

        this.location = Location.fromByte(bytes[3]);
        this.pressure = Property.getFloat(bytes, 4);
    }
}
