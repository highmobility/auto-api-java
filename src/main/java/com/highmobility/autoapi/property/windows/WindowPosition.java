package com.highmobility.autoapi.property.windows;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;

public class WindowPosition extends Property {
    WindowLocation location;
    Position position;

    /**
     * @return The window location.
     */
    public WindowLocation getLocation() {
        return location;
    }

    /**
     * @return The window position.
     */
    public Position getPosition() {
        return position;
    }

    public WindowPosition(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 5) throw new CommandParseException();
        location = WindowLocation.fromByte(bytes[3]);
        position = Position.fromByte(bytes[4]);
    }

    public WindowPosition(WindowLocation location, Position
            position) {
        super((byte) 0x00, getBytes(location, position));
        this.location = location;
        this.position = position;
    }

    private static byte[] getBytes(WindowLocation location, Position position) {
        byte[] bytes = new byte[2];
        bytes[0] = location.getByte();
        bytes[1] = position.getByte();
        return bytes;
    }

    public enum Position {
        CLOSED((byte) 0x00),
        OPEN((byte) 0x01),
        INTERMEDIATE((byte) 0x02);

        public static Position fromByte(byte value) throws CommandParseException {
            Position[] allValues = Position.values();

            for (int i = 0; i < allValues.length; i++) {
                Position value1 = allValues[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Position(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}
