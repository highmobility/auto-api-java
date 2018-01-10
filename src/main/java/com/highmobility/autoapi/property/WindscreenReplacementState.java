package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum WindscreenReplacementState implements HMProperty {
    UNKNOWN((byte)0x00),
    REPLACEMENT_NOT_NEEDED((byte)0x01),
    REPLACEMENT_NEEDED((byte)0x02);

    public static WindscreenReplacementState fromByte(byte byteValue) throws CommandParseException {
        WindscreenReplacementState[] values = WindscreenReplacementState.values();

        for (int i = 0; i < values.length; i++) {
            WindscreenReplacementState state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    WindscreenReplacementState(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }

    @Override public byte getPropertyIdentifier() {
        return 0x06;
    }

    @Override public int getPropertyLength() {
        return 1;
    }

    @Override public byte[] getPropertyBytes() {
        return Property.getPropertyBytes(getPropertyIdentifier(), getPropertyLength(), value);
    }
}