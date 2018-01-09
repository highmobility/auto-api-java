package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum ScreenLocation implements HMProperty {
    FRONT((byte)0x00),
    REAR((byte)0x01);

    public static ScreenLocation fromByte(byte byteValue) throws CommandParseException {
        ScreenLocation[] values = ScreenLocation.values();

        for (int i = 0; i < values.length; i++) {
            ScreenLocation state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    ScreenLocation(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }

    @Override public byte getPropertyIdentifier() {
        return 0x03;
    }

    @Override public int getPropertyLength() {
        return 1;
    }

    @Override public byte[] getPropertyBytes() {
        return Property.getPropertyBytes(getPropertyIdentifier(), getPropertyLength(), value);
    }
}