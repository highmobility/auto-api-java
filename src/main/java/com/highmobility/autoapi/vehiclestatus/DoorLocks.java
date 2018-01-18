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

package com.highmobility.autoapi.vehiclestatus;
import com.highmobility.autoapi.Command.Identifier;
import com.highmobility.autoapi.CommandParseException;

import com.highmobility.autoapi.DoorLockState;

import java.util.ArrayList;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class DoorLocks extends FeatureState {
    DoorLockState frontLeft;
    DoorLockState frontRight;
    DoorLockState rearRight;
    DoorLockState rearLeft;

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
     * @return the current lock/position state of the rear right door
     */
    public DoorLockState getRearRight() {
        return rearRight;
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

    public DoorLocks(DoorLockState frontLeft,
                     DoorLockState frontRight,
                     DoorLockState rearRight,
                     DoorLockState rearLeft) {
        super(Identifier.DOOR_LOCKS);
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.rearRight = rearRight;
        this.rearLeft = rearLeft;

        ArrayList<DoorLockState> lockStates = new ArrayList<>();

        if (frontLeft != null) { lockStates.add(frontLeft); }
        if (frontRight != null) { lockStates.add(frontRight); }
        if (rearLeft != null) { lockStates.add(rearRight); }
        if (rearRight != null) { lockStates.add(rearLeft); }

        bytes = getBytesWithMoreThanOneByteLongFields(1 + lockStates.size(), (lockStates.size() * 2));
        bytes[2] = (byte) (1 + lockStates.size() * 3);
        bytes[3] = (byte) lockStates.size();

        int position = 4;
        for (int i = 0; i < lockStates.size(); i++) {
            DoorLockState state = lockStates.get(i);
            bytes[position] = state.getLocation().getByte();
            bytes[position + 1] = state.getPosition().getByte();
            bytes[position + 2] = state.getLockState().getByte();
            position += 3;
        }
    }

    public DoorLocks(byte[] bytes) throws CommandParseException {
        super(Identifier.DOOR_LOCKS);

        if (bytes.length < 5) throw new CommandParseException();
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
        this.bytes = bytes;
    }
}