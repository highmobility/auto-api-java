package com.highmobility.autoapi.value.tachograph;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.PropertyValueSingleByte;

public enum VehicleDirection implements PropertyValueSingleByte {
    FORWARD((byte) 0x00),
    REVERSE((byte) 0x01);

    public static VehicleDirection fromByte(byte byteValue) throws CommandParseException {
        VehicleDirection[] values = VehicleDirection.values();

        for (int i = 0; i < values.length; i++) {
            VehicleDirection state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    VehicleDirection(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}