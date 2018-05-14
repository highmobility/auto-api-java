package com.highmobility.autoapi.property.doors;

import com.highmobility.autoapi.CommandParseException;

/**
 * The possible states of the car doorLock.
 */
public enum DoorLock {
    UNLOCKED((byte) 0x00),
    LOCKED((byte) 0x01);

    public static DoorLock fromByte(byte value) throws CommandParseException {
        DoorLock[] values = DoorLock.values();

        for (int i = 0; i < values.length; i++) {
            DoorLock value1 = values[i];
            if (value1.getByte() == value) {
                return value1;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    DoorLock(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}