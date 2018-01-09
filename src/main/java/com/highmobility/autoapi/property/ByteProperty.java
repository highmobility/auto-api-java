package com.highmobility.autoapi.property;

public class ByteProperty extends Property {
    public ByteProperty(byte identifier, byte value) {
        super(identifier, 1);
        bytes[3] = value;
    }
}
