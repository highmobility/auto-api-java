// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.ByteEnum;

public enum ActiveState implements ByteEnum {
    INACTIVE((byte) 0x00),
    ACTIVE((byte) 0x01);

    public static ActiveState fromByte(byte byteValue) throws CommandParseException {
        ActiveState[] values = ActiveState.values();

        for (int i = 0; i < values.length; i++) {
            ActiveState state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    ActiveState(byte value) {
        this.value = value;
    }

    @Override public byte getByte() {
        return value;
    }
}