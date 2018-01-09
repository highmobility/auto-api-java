package com.highmobility.autoapi.property;

import com.highmobility.utils.Bytes;

public class IntProperty extends Property {
    public IntProperty(byte identifier, int value, int length) {
        super(identifier, length);

        if (length == 1) {
            bytes[3] = (byte)value;
        }
        else {
            Bytes.setBytes(bytes, intToBytes(value, length), 3);
        }
    }
}
