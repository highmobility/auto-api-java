// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;

public enum FluidLevel {
    LOW((byte)0x00),
    FILLED((byte)0x01);

    public static FluidLevel fromByte(byte byteValue) throws CommandParseException {
        FluidLevel[] values = FluidLevel.values();

        for (int i = 0; i < values.length; i++) {
            FluidLevel state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    FluidLevel(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}