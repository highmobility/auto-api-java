// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;

public enum WindowLocation {
    FRONT_LEFT((byte)0x00),
    FRONT_RIGHT((byte)0x01),
    REAR_RIGHT((byte)0x02),
    REAR_LEFT((byte)0x03),
    HATCH((byte)0x04);

    public static WindowLocation fromByte(byte byteValue) throws CommandParseException {
        WindowLocation[] values = WindowLocation.values();

        for (int i = 0; i < values.length; i++) {
            WindowLocation state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    WindowLocation(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}