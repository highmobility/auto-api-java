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

package com.highmobility.autoapi.property.doors;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Position;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.value.Location;

public class DoorPosition extends Property {
    Location location;
    Position position;

    /**
     * @return The door location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The door lock state.
     */
    public Position getPosition() {
        return position;
    }

    public DoorPosition(byte[] bytes) throws CommandParseException {
        this(Location.fromByte(bytes[6]), Position.fromByte(bytes[7]));
    }

    public DoorPosition(Location location, Position position) {
        super((byte) 0x01, 2);
        this.location = location;
        this.position = position;

        bytes[6] = location.getByte();
        bytes[7] = position.getByte();
    }
}