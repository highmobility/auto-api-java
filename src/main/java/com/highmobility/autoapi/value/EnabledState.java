// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.ByteEnum;

public enum EnabledState implements ByteEnum {
    DISABLED((byte) 0x00),
    ENABLED((byte) 0x01);

    public static EnabledState fromByte(byte byteValue) throws CommandParseException {
        EnabledState[] values = EnabledState.values();

        for (int i = 0; i < values.length; i++) {
            EnabledState state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    EnabledState(byte value) {
        this.value = value;
    }

    @Override public byte getByte() {
        return value;
    }
}