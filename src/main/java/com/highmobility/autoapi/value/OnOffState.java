// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.ByteEnum;

public enum OnOffState implements ByteEnum {
    OFF((byte) 0x00),
    ON((byte) 0x01);

    public static OnOffState fromByte(byte byteValue) throws CommandParseException {
        OnOffState[] values = OnOffState.values();

        for (int i = 0; i < values.length; i++) {
            OnOffState state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    OnOffState(byte value) {
        this.value = value;
    }

    @Override public byte getByte() {
        return value;
    }
}