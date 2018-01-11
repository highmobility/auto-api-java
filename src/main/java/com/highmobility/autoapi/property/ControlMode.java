package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

/**
 * The possible control modes
 */
public enum ControlMode implements HMProperty {
    UNAVAILABLE((byte)0x00),
    AVAILABLE((byte)0x01),
    STARTED((byte)0x02),
    FAILED_TO_START((byte)0x03),
    ABORTED((byte)0x04),
    ENDED((byte)0x05),
    UNSUPPORTED((byte)0xFF);

    public static ControlMode fromByte(byte value) throws CommandParseException {
        ControlMode[] allValues = ControlMode.values();

        for (int i = 0; i < allValues.length; i++) {
            ControlMode value1 = allValues[i];
            if (value1.getByte() == value) {
                return value1;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    ControlMode(byte value) {
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