// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;

public enum DrivingMode {
    REGULAR((byte)0x00),
    ECO((byte)0x01),
    SPORT((byte)0x02),
    SPORT_PLUS((byte)0x03),
    ECOPLUS((byte)0x04),
    COMFORT((byte)0x05);

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
}