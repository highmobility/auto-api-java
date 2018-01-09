package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum Axle {
    FRONT((byte)0x00),
    REAR((byte)0x01);

    public static Axle fromByte(byte byteValue) throws CommandParseException {
        Axle[] values = Axle.values();

        for (int i = 0; i < values.length; i++) {
            Axle state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    Axle(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}
