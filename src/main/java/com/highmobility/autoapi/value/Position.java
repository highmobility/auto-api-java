// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.ByteEnum;

public enum Position implements ByteEnum {
    CLOSED((byte) 0x00),
    OPEN((byte) 0x01);

    public static Position fromByte(byte byteValue) throws CommandParseException {
        Position[] values = Position.values();

        for (int i = 0; i < values.length; i++) {
            Position state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    Position(byte value) {
        this.value = value;
    }

    @Override public byte getByte() {
        return value;
    }
}