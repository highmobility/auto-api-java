package com.highmobility.autoapi.property.charging;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;

public enum PlugType implements HMProperty {
    TYPE_1((byte)0x00),
    TYPE_2((byte)0x01),
    COMBINED_CHARGING_SYSTEM((byte)0x02),
    CHA_DE_MO((byte)0x03);

    public static final byte IDENTIFIER = 0x0F;

    public static PlugType fromByte(byte byteValue) throws CommandParseException {
        PlugType[] values = PlugType.values();

        for (int i = 0; i < values.length; i++) {
            PlugType state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    PlugType(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }

    @Override public byte getPropertyIdentifier() {
        return IDENTIFIER;
    }

    @Override public int getPropertyLength() {
        return 1;
    }

    @Override public byte[] getPropertyBytes() {
        return Property.getPropertyBytes(getPropertyIdentifier(), value);
    }
}
