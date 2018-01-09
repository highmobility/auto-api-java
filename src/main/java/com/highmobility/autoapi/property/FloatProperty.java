package com.highmobility.autoapi.property;

import com.highmobility.utils.Bytes;

public class FloatProperty extends Property {
    public FloatProperty(byte identifier, float value) {
        super(identifier, 4);
        Bytes.setBytes(bytes, floatToBytes(value), 3);
    }
}
