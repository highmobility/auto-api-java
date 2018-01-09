package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum FatigueLevel implements HMProperty {
    LIGHT_FATIGUE((byte)0x00),
    PAUSE_RECOMMENDED((byte)0x01),
    ACTION_NEEDED_DRIVER_NEEDS_REST((byte)0x02),
    CAR_READY_TO_TAKE_OVER((byte)0x03);

    public static FatigueLevel fromByte(byte byteValue) throws CommandParseException {
        FatigueLevel[] values = FatigueLevel.values();

        for (int i = 0; i < values.length; i++) {
            FatigueLevel state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    FatigueLevel(byte value) {
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