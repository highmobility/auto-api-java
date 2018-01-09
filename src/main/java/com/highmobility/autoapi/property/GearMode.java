package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum GearMode implements HMProperty {
    MANUAL((byte)0x00),
    PARK((byte)0x01),
    REVERSE((byte)0x02),
    NEUTRAL((byte)0x03),
    DRIVE((byte)0x04),
    LOW_GEAR((byte)0x05),
    SPORT((byte)0x06);

    public static GearMode fromByte(byte byteValue) throws CommandParseException {
        GearMode[] values = GearMode.values();

        for (int i = 0; i < values.length; i++) {
            GearMode state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    GearMode(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }

    @Override public byte getPropertyIdentifier() {
        return 0x0B;
    }

    @Override public int getPropertyLength() {
        return 1;
    }

    @Override public byte[] getPropertyBytes() {
        return Property.getPropertyBytes(getPropertyIdentifier(), getPropertyLength(), value);
    }
}