package com.highmobility.autoapi.value.lights;

import com.highmobility.autoapi.CommandParseException;

public enum LightLocation {
    FRONT((byte) 0x00),
    REAR((byte) 0x01);

    public static LightLocation fromByte(byte value) throws CommandParseException {
        LightLocation[] values = LightLocation.values();

        for (int i = 0; i < values.length; i++) {
            LightLocation capability = values[i];
            if (capability.getByte() == value) {
                return capability;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    LightLocation(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}
