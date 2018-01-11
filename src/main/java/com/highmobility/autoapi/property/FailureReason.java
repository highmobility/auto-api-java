package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum FailureReason implements HMProperty {
    UNSUPPORTED_CAPABILITY((byte) 0x00),
    UNAUTHORIZED((byte) 0x01),
    INCORRECT_STATE((byte) 0x02),
    EXECUTION_TIMEOUT((byte) 0x03),
    VEHICLE_ASLEEP((byte) 0x04),
    INVALID_COMMAND((byte) 0x05);

    public byte getByte() {
        return value;
    }

    public static FailureReason fromByte(byte value) throws CommandParseException {
        FailureReason[] allValues = FailureReason.values();

        for (int i = 0; i < allValues.length; i++) {
            FailureReason value1 = allValues[i];

            if (value1.getByte() == value) {
                return value1;
            }
        }

        throw new CommandParseException();
    }

    FailureReason(byte reason) {
        this.value = reason;
    }

    private byte value;

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