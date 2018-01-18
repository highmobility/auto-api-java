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
 * Created by root on 6/29/17.
 */

public class WindowState {
    public enum Location {
        FRONT_LEFT, FRONT_RIGHT, REAR_LEFT, REAR_RIGHT;

        byte getByte() {
            switch (this) {
                case FRONT_LEFT:
                    return 0x00;
                case FRONT_RIGHT:
                    return 0x01;
                case REAR_RIGHT:
                    return 0x02;
                case REAR_LEFT:
                    return 0x03;
            }

            return 0x00;
        }

        public static Location fromByte(byte value) throws CommandParseException {
            switch (value) {
                case 0x00: return FRONT_LEFT;
                case 0x01: return FRONT_RIGHT;
                case 0x02: return REAR_RIGHT;
                case 0x03: return REAR_LEFT;
            }

            throw new CommandParseException();
        }
    }

    public enum Position {
        OPEN, CLOSED;

        byte getByte() {
            switch (this) {
                case OPEN:
                    return 0x01;
                case CLOSED:
                    return 0x00;
            }

            return 0x00;
        }

        public static Position fromByte(byte value) throws CommandParseException {
            switch (value) {
                case 0x01: return OPEN;
                case 0x00: return CLOSED;
            }

            throw new CommandParseException();
        }
    }

    Location location;
    Position position;

    public Location getLocation() {
        return location;
    }

    public Position getPosition() {
        return position;
    }

    public WindowState(Location location, Position position) {
        this.location = location;
        this.position = position;
    }

    public byte[] getBytes() {
        return new byte[] { location.getByte(), position.getByte() };
    }
}
