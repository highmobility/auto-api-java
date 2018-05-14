package com.highmobility.autoapi.property.doors;

import com.highmobility.autoapi.CommandParseException;

/**
 * The possible positions of a car door.
 */
public enum DoorPosition {
    CLOSED((byte) 0x00),
    OPEN((byte) 0x01);

    public static DoorPosition fromByte(byte value) throws CommandParseException {
        DoorPosition[] values = DoorPosition.values();

        for (int i = 0; i < values.length; i++) {
            DoorPosition value1 = values[i];
            if (value1.getByte() == value) {
                return value1;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    DoorPosition(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}