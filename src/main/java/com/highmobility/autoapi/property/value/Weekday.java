package com.highmobility.autoapi.property.value;

import com.highmobility.autoapi.CommandParseException;

public enum Weekday {
    MONDAY((byte) 0x00),
    TUESDAY((byte) 0x01),
    WEDNESDAY((byte) 0x02),
    THURSDAY((byte) 0x03),
    FRIDAY((byte) 0x04),
    SATURDAY((byte) 0x05),
    SUNDAY((byte) 0x06),
    AUTOMATIC((byte) 0x07);

    public static Weekday fromByte(byte value) throws CommandParseException {
        Weekday[] values = Weekday.values();

        for (int i = 0; i < values.length; i++) {
            Weekday value1 = values[i];
            if (value1.getByte() == value) {
                return value1;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    Weekday(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}