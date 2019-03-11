package com.highmobility.autoapi.property.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.PropertyValueSingleByte;

/**
 * The possible states of the car doorLock.
 */
public enum Lock implements PropertyValueSingleByte {
    UNLOCKED((byte) 0x00),
    LOCKED((byte) 0x01);

    public static Lock fromByte(byte value) throws CommandParseException {
        Lock[] values = Lock.values();

        for (int i = 0; i < values.length; i++) {
            Lock value1 = values[i];
            if (value1.getByte() == value) {
                return value1;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    Lock(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}