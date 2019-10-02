// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.ByteEnum;

public enum LocationLongitudinal implements ByteEnum {
    FRONT((byte) 0x00),
    REAR((byte) 0x01);

    public static LocationLongitudinal fromByte(byte byteValue) throws CommandParseException {
        LocationLongitudinal[] values = LocationLongitudinal.values();

        for (int i = 0; i < values.length; i++) {
            LocationLongitudinal state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    LocationLongitudinal(byte value) {
        this.value = value;
    }

    @Override public byte getByte() {
        return value;
    }
}