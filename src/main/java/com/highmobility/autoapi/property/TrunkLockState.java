package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum TrunkLockState implements HMProperty {
    UNLOCKED((byte) 0x00),
    LOCKED((byte) 0x01);

    public static final byte defaultIdentifier = 0x01;

    public static TrunkLockState fromByte(byte value) throws CommandParseException {
        TrunkLockState[] allValues = TrunkLockState.values();

        for (int i = 0; i < allValues.length; i++) {
            TrunkLockState value1 = allValues[i];
            if (value1.getByte() == value) {
                return value1;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    TrunkLockState(byte value) {
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