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
