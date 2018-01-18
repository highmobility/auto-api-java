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

package com.highmobility.autoapi.incoming;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.DoorLockState;

/**
 * Created by ttiganik on 13/09/16.
 *
 * This is an evented notification that is sent from the car every time the lock state changes. This
 * notificaiton is also sent when a Get Lock State is received by the car. The new status is included
 * and may be the result of user, device or car triggered action.
 */
public class LockState extends IncomingCommand {
    DoorLockState frontLeft;
    DoorLockState frontRight;
    DoorLockState rearLeft;
    DoorLockState rearRight;

    /**
     *
     * @return the current lock/position state of the front left door
     */
    public DoorLockState getFrontLeft() {
        return frontLeft;
    }

    /**
     *
     * @return the current lock/position state of the front right door
     */
    public DoorLockState getFrontRight() {
        return frontRight;
    }

    /**
     *
     * @return the current lock/position state of the rear left door
     */
    public DoorLockState getRearLeft() {
        return rearLeft;
    }

    /**
     *
     * @return the current lock/position state of the rear right door
     */
    public DoorLockState getRearRight() {
        return rearRight;
    }

    /**
     *
     * @return true if all doors are closed and locked, otherwise false
     */
    public boolean isLocked() {
        if (getFrontLeft() != null && getFrontLeft().getLockState() != DoorLockState.LockState.LOCKED) {
            return false;
        }

        if (getFrontRight() != null && getFrontRight().getLockState() != DoorLockState.LockState.LOCKED) {
            return false;
        }

        if (getRearLeft() != null && getRearLeft().getLockState() != DoorLockState.LockState.LOCKED) {
            return false;
        }

        if (getRearRight() != null && getRearRight().getLockState() != DoorLockState.LockState.LOCKED) {
            return false;
        }

        return true;
    }

    public LockState(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length < 4) throw new CommandParseException();
        int numberOfDoors = bytes[3];
        int position = 4;

        for (int i = 0; i < numberOfDoors; i++) {
            byte location = bytes[position];
            DoorLockState doorLockState = new DoorLockState(location, bytes[position + 1], bytes[position + 2]);
            if (doorLockState.getLocation() == DoorLockState.Location.FRONT_LEFT) {
                frontLeft = doorLockState;
            }
            else if (doorLockState.getLocation() == DoorLockState.Location.FRONT_RIGHT) {
                frontRight = doorLockState;
            }
            else if (doorLockState.getLocation() == DoorLockState.Location.REAR_RIGHT) {
                rearRight = doorLockState;
            }
            else if (doorLockState.getLocation() == DoorLockState.Location.REAR_LEFT) {
                rearLeft = doorLockState;
            }

            position += 3;
        }

        //002001040001000100000200010300
    }
}
