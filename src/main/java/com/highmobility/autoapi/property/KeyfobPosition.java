package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum KeyfobPosition implements HMProperty {
    OUT_OF_RANGE((byte)0x00),
    OUTSIDE_DRIVER_SIDE((byte)0x01),
    OUTSIDE_IN_FRONT_OF_CAR((byte)0x02),
    OUTSIDE_PASSENGER_SIDE((byte)0x03),
    OUTSIDE_BEHIND_CAR((byte)0x04),
    INSIDE_CAR((byte)0x05);

    public static KeyfobPosition fromByte(byte value) throws CommandParseException {
        KeyfobPosition[] values = KeyfobPosition.values();

        for (int i = 0; i < values.length; i++) {
            KeyfobPosition possibleValue = values[i];
            if (possibleValue.getByte() == value) {
                return possibleValue;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    KeyfobPosition(byte value) {
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