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

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.value.Lock;

/**
 * Command to lock or unlock all doors of the car. The car will respond with the updated lock state
 * in a Lock State message.
 */
public class LockUnlockDoors extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.DOOR_LOCKS, 0x12);

    private static final byte IDENTIFIER = 0x01;

    Lock.Value doorLock;

    /**
     * @return The lock state.
     */
    public Lock.Value getDoorLock() {
        return doorLock;
    }

    public LockUnlockDoors(Lock.Value doorLock) {
        super(TYPE.addProperty(new Property(IDENTIFIER, doorLock.getByte())));
        this.doorLock = doorLock;
    }

    LockUnlockDoors(byte[] bytes) throws CommandParseException {
        super(bytes);
        Property prop = getProperty(IDENTIFIER);
        if (prop == null) throw new CommandParseException();
        doorLock = Lock.Value.fromByte(prop.getValueByte());
    }
}
