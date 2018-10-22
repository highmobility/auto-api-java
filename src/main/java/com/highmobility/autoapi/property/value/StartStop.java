package com.highmobility.autoapi.property.value;

import com.highmobility.autoapi.CommandParseException;

public enum StartStop  {
    START((byte)0x00),
    STOP((byte)0x01),
    RESET((byte)0x02);

    public static StartStop fromByte(byte byteValue) throws CommandParseException {
        StartStop[] values = StartStop.values();

        for (int i = 0; i < values.length; i++) {
            StartStop state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    StartStop(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}
