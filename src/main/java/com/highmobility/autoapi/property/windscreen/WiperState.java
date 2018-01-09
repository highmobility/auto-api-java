package com.highmobility.autoapi.property.windscreen;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;

public enum WiperState implements HMProperty {
    INACTIVE((byte)0x00),
    ACTIVE((byte)0x01),
    AUTOMATIC((byte)0x02);

    public static WiperState fromByte(byte byteValue) throws CommandParseException {
        WiperState[] values = WiperState.values();

        for (int i = 0; i < values.length; i++) {
            WiperState state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    WiperState(byte value) {
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