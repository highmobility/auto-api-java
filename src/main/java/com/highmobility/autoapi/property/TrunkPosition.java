package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum TrunkPosition implements HMProperty {
    CLOSED((byte) 0x00),
    OPEN((byte) 0x01);

    public static final byte defaultIdentifier = 0x02;

    public static TrunkPosition fromByte(byte value) throws CommandParseException {
        TrunkPosition[] allValues = TrunkPosition.values();

        for (int i = 0; i < allValues.length; i++) {
            TrunkPosition value1 = allValues[i];
            if (value1.getByte() == value) {
                return value1;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    TrunkPosition(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }

    @Override public byte getPropertyIdentifier() {
        return defaultIdentifier;
    }

    @Override public int getPropertyLength() {
        return 1;
    }

    @Override public byte[] getPropertyBytes() {
        return Property.getPropertyBytes(getPropertyIdentifier(), getPropertyLength(), value);
    }
}