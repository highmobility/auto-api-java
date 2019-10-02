// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.ByteEnum;

public enum LockState implements ByteEnum {
    UNLOCKED((byte) 0x00),
    LOCKED((byte) 0x01);

    public static LockState fromByte(byte byteValue) throws CommandParseException {
        LockState[] values = LockState.values();

        for (int i = 0; i < values.length; i++) {
            LockState state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    LockState(byte value) {
        this.value = value;
    }

    @Override public byte getByte() {
        return value;
    }
}