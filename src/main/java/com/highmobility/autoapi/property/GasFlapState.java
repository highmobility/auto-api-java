package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum GasFlapState implements HMProperty {
    CLOSED((byte)0x00),
    OPEN((byte)0x01);

    public static GasFlapState fromByte(byte byteValue) throws CommandParseException {
        GasFlapState[] values = GasFlapState.values();

        for (int i = 0; i < values.length; i++) {
            GasFlapState state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    GasFlapState(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }

    @Override public byte getPropertyIdentifier() {
        return 0x01;
    }

    @Override public int getPropertyLength() {
        return 1;
    }

    @Override public byte[] getPropertyBytes() {
        return Property.getPropertyBytes(getPropertyIdentifier(), getPropertyLength(), value);
    }
}