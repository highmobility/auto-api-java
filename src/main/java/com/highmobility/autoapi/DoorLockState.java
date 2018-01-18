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

/**
 * Created by root on 6/21/17.
 */

public class DoorLockState {
    /**
     * The door location
     */
    public enum Location {
        FRONT_LEFT((byte)0x00),
        FRONT_RIGHT((byte)0x01),
        REAR_RIGHT((byte)0x02),
        REAR_LEFT((byte)0x03);

        public static Location fromByte(byte value) throws CommandParseException {
            Location[] capabilities = Location.values();

            for (int i = 0; i < capabilities.length; i++) {
                Location capability = capabilities[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        Location(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    /**
     * The possible positions of a car door
     */
    public enum Position {
        CLOSED((byte)0x00),
        OPEN((byte)0x01);

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
        UNLOCKED((byte)0x00),
        LOCKED((byte)0x01);

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

    Location location;
    Position position;
    LockState lockState;

    public DoorLockState(byte location, byte position, byte lockState) throws CommandParseException {
        this.location = Location.fromByte(location);
        this.position = Position.fromByte(position);
        this.lockState = LockState.fromByte(lockState);
    }

    public DoorLockState(Location location, Position position, LockState lockState) {
        this.location = location;
        this.position = position;
        this.lockState = lockState;
    }

    public Location getLocation() {
        return location;
    }

    public Position getPosition() {
        return position;
    }

    public LockState getLockState() {
        return lockState;
    }
}