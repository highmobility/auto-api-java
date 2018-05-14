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
import com.highmobility.autoapi.property.Property;

public class DoorLockState extends Property {
    DoorLocation doorLocation;
    DoorLock doorLock;

    /**
     * @return The door location.
     */
    public DoorLocation getDoorLocation() {
        return doorLocation;
    }

    /**
     * @return The door lock state.
     */
    public DoorLock getDoorLock() {
        return doorLock;
    }

    public DoorLockState(byte[] bytes) throws CommandParseException {
        this(DoorLocation.fromByte(bytes[3]), DoorLock.fromByte
                (bytes[4]));
    }

    public DoorLockState(DoorLocation doorLocation, DoorLock doorLock) {
        super((byte) 0x01, 2);
        this.doorLocation = doorLocation;
        this.doorLock = doorLock;

        bytes[3] = doorLocation.getByte();
        bytes[4] = doorLock.getByte();
    }
}