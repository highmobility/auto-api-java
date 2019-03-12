package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;

/**
 * The tire location.
 */
public enum TireLocation {
    FRONT_LEFT((byte) 0x00),
    FRONT_RIGHT((byte) 0x01),
    REAR_RIGHT((byte) 0x02),
    REAR_LEFT((byte) 0x03);

    public static TireLocation fromByte(byte value) throws CommandParseException {
        TireLocation[] values = TireLocation.values();

        for (int i = 0; i < values.length; i++) {
            TireLocation value1 = values[i];
            if (value1.getByte() == value) {
                return value1;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    TireLocation(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}
