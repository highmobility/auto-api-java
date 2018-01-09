package com.highmobility.autoapi.property;

public class ColorProperty extends Property {
    public ColorProperty(byte identifier, int[] value) throws IllegalArgumentException {
        super(identifier, 3);
        if (value.length != 4) throw new IllegalArgumentException("Need 4 color values");
        bytes[3] = (byte) value[0];
        bytes[4] = (byte) value[1];
        bytes[5] = (byte) value[2];
    }
}
