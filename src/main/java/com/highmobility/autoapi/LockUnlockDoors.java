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

import com.highmobility.autoapi.property.DoorLockProperty;

/**
 * Attempt to lock or unlock all doors of the car. The result is not received by the ack but instead
 * sent through the evented Lock State message with either the mode 0x00 Unlocked or 0x01 Locked.
 */
public class LockUnlockDoors extends Command {
    public static final Type TYPE = new Type(Identifier.DOOR_LOCKS, 0x02);

    DoorLockProperty.LockState lockState;

    /**
     *
     * @return The door lock state
     */
    public DoorLockProperty.LockState getLockState() {
        return lockState;
    }

    public LockUnlockDoors(DoorLockProperty.LockState state) {
        super(TYPE.addByte(state.getByte()), true);
        this.lockState = state;
    }

    LockUnlockDoors(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length != 4) throw new CommandParseException();
        lockState = DoorLockProperty.LockState.fromByte(bytes[3]);
    }
}