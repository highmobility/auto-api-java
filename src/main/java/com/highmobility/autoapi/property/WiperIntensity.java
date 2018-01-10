package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum WiperIntensity implements HMProperty {
    LEVEL_0((byte)0x00),
    LEVEL_1((byte)0x01),
    LEVEL_2((byte)0x02),
    LEVEL_3((byte)0x03);

    public static WiperIntensity fromByte(byte byteValue) throws CommandParseException {
        WiperIntensity[] values = WiperIntensity.values();

        for (int i = 0; i < values.length; i++) {
            WiperIntensity state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    WiperIntensity(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }

    @Override public byte getPropertyIdentifier() {
        return 0x02;
    }

    @Override public int getPropertyLength() {
        return 1;
    }

    @Override public byte[] getPropertyBytes() {
        return Property.getPropertyBytes(getPropertyIdentifier(), getPropertyLength(), value);
    }
}