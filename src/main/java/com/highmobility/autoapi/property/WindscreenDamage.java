package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum WindscreenDamage implements HMProperty {
    NO_IMPACT((byte)0x00),
    IMPACT_NO_DAMAGE((byte)0x01),
    DAMAGE_SMALLER_THAN_1((byte)0x02),
    DAMAGE_LARGER_THAN_1((byte)0x03);

    public static WindscreenDamage fromByte(byte byteValue) throws CommandParseException {
        WindscreenDamage[] values = WindscreenDamage.values();

        for (int i = 0; i < values.length; i++) {
            WindscreenDamage state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    WindscreenDamage(byte value) {
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