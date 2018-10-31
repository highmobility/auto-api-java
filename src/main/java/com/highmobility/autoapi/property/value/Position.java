package com.highmobility.autoapi.property.value;

import com.highmobility.autoapi.CommandParseException;

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
