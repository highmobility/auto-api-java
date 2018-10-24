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

public class WheelRpm extends Property {
    Location location;
    int rpm;

    /**
     * @return The wheel location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The wheel's RPM.
     */
    public int getRpm() {
        return rpm;
    }

    public WheelRpm(Location location, int rpm) {
        super((byte) 0x00, 3);
        this.location = location;
        this.rpm = rpm;
        this.bytes[3] = location.getByte();
        ByteUtils.setBytes(bytes, Property.intToBytes(rpm, 2), 4);
    }

    public WheelRpm(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 6) throw new CommandParseException();

        this.location = Location.fromByte(bytes[3]);
        this.rpm = Property.getUnsignedInt(bytes, 4, 2);
    }
}
