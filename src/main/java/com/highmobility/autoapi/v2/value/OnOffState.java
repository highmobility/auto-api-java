// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;

public enum OnOffState {
    OFF((byte)0x00),
    ON((byte)0x01);

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

    public byte getByte() {
        return value;
    }
}