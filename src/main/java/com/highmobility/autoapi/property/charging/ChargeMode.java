package com.highmobility.autoapi.property.charging;

import com.highmobility.autoapi.CommandParseException;

/**
 * The charge mode.
 */
public enum ChargeMode {
    IMMEDIATE((byte) 0x00),
    TIMER_BASED((byte) 0x01),
    INDUCTIVE((byte) 0x02);

    public static ChargeMode fromByte(byte byteValue) throws CommandParseException {
        ChargeMode[] values = ChargeMode.values();

        for (int i = 0; i < values.length; i++) {
            ChargeMode state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    ChargeMode(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}