// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.ByteEnum;

public enum Detected implements ByteEnum {
    NOT_DETECTED((byte) 0x00),
    DETECTED((byte) 0x01);

    public static Detected fromByte(byte byteValue) throws CommandParseException {
        Detected[] values = Detected.values();

        for (int i = 0; i < values.length; i++) {
            Detected state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    Detected(byte value) {
        this.value = value;
    }

    @Override public byte getByte() {
        return value;
    }
}