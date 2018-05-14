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

public class DoorLockAndPositionState extends Property {
    public static final byte IDENTIFIER = 0x01;

    DoorLocation doorLocation;
    DoorPosition doorPosition;
    DoorLock doorLock;

    /**
     * @return The door door location.
     */
    public DoorLocation getDoorLocation() {
        return doorLocation;
    }

    /**
     * @return The door door position.
     */
    public DoorPosition getDoorPosition() {
        return doorPosition;
    }

    /**
     * @return The door lock state.
     */
    public DoorLock getDoorLock() {
        return doorLock;
    }

    public DoorLockAndPositionState(byte[] bytes) throws CommandParseException {
        this(DoorLocation.fromByte(bytes[3]), DoorPosition.fromByte(bytes[4]), DoorLock.fromByte
                (bytes[5]));
    }

    public DoorLockAndPositionState(DoorLocation doorLocation, DoorPosition doorPosition,
                                    DoorLock doorLock) {
        super(IDENTIFIER, 3);
        this.doorLocation = doorLocation;
        this.doorPosition = doorPosition;
        this.doorLock = doorLock;

        bytes[3] = doorLocation.getByte();
        bytes[4] = doorPosition.getByte();
        bytes[5] = doorLock.getByte();
    }
}