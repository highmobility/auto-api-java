// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.ByteEnum;

public enum WindowLocation implements ByteEnum {
    FRONT_LEFT((byte) 0x00),
    FRONT_RIGHT((byte) 0x01),
    REAR_RIGHT((byte) 0x02),
    REAR_LEFT((byte) 0x03),
    HATCH((byte) 0x04);

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

    @Override public byte getByte() {
        return value;
    }
}