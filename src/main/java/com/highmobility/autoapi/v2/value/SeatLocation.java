// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;

public enum SeatLocation {
    FRONT_LEFT((byte)0x00),
    FRONT_RIGHT((byte)0x01),
    REAR_RIGHT((byte)0x02),
    REAR_LEFT((byte)0x03),
    REAR_CENTER((byte)0x04);

    public static SeatLocation fromByte(byte byteValue) throws CommandParseException {
        SeatLocation[] values = SeatLocation.values();

        for (int i = 0; i < values.length; i++) {
            SeatLocation state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    SeatLocation(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}