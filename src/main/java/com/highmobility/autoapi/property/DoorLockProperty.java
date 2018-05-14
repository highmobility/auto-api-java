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
import com.highmobility.autoapi.property.doors.DoorLockAndPositionState;

/**
 * @deprecated use {@link DoorLockAndPositionState} instead
 */
@Deprecated
public class DoorLockProperty extends Property {
    Location location;
    Position position;
    LockState lockState;

    /**
     * @return The door location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The door position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @return The lock state
     */
    public LockState getLockState() {
        return lockState;
    }

    public DoorLockProperty(byte[] bytes) throws CommandParseException {
        this(Location.fromByte(bytes[3]), Position.fromByte(bytes[4]), LockState.fromByte
                (bytes[5]));
    }

    public DoorLockProperty(Location location, Position position, LockState lockState) {
        super((byte) 0x01, 3);
        this.location = location;
        this.position = position;
        this.lockState = lockState;

        bytes[3] = location.getByte();
        bytes[4] = position.getByte();
        bytes[5] = lockState.getByte();
    }

    /**
     * The door location
     */
    public enum Location {
        FRONT_LEFT((byte) 0x00),
        FRONT_RIGHT((byte) 0x01),
        REAR_RIGHT((byte) 0x02),
        REAR_LEFT((byte) 0x03);

        public static Location fromByte(byte value) throws CommandParseException {
            Location[] allValues = Location.values();

            for (int i = 0; i < allValues.length; i++) {
                Location value1 = allValues[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Location(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }

    /**
     * The possible positions of a car door
     */
    public enum Position {
        CLOSED((byte) 0x00),
        OPEN((byte) 0x01);

        public static Position fromByte(byte value) throws CommandParseException {
            Position[] capabilities = Position.values();

            for (int i = 0; i < capabilities.length; i++) {
                Position capability = capabilities[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        Position(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    /**
     * The possible states of the car lock.
     */
    public enum LockState {
        UNLOCKED((byte) 0x00),
        LOCKED((byte) 0x01);

        public static LockState fromByte(byte value) throws CommandParseException {
            LockState[] capabilities = LockState.values();

            for (int i = 0; i < capabilities.length; i++) {
                LockState capability = capabilities[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        LockState(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }
}