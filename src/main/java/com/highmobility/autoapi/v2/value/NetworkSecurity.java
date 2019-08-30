// TODO: license
package com.highmobility.autoapi.v2.value;

import com.highmobility.autoapi.v2.CommandParseException;

public enum NetworkSecurity {
    NONE((byte)0x00),
    WEP((byte)0x01),
    WPA((byte)0x02),
    WPA2_PERSONAL((byte)0x03);

    public static NetworkSecurity fromByte(byte byteValue) throws CommandParseException {
        NetworkSecurity[] values = NetworkSecurity.values();

        for (int i = 0; i < values.length; i++) {
            NetworkSecurity state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    NetworkSecurity(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}