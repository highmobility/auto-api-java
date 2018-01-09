package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum DrivingMode implements HMProperty {
    REGULAR((byte)0x00),
    ECO((byte)0x01),
    SPORT((byte)0x02),
    SPORT_PLUS((byte)0x03);

    public static DrivingMode fromByte(byte byteValue) throws CommandParseException {
        DrivingMode[] values = DrivingMode.values();

        for (int i = 0; i < values.length; i++) {
            DrivingMode state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    DrivingMode(byte value) {
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