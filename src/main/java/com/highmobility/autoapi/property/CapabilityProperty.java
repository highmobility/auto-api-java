package com.highmobility.autoapi.property;

import com.highmobility.autoapi.Type;
import com.highmobility.utils.Bytes;

public class CapabilityProperty extends Property {
    // TODO:
    Type[] types;

    public CapabilityProperty(Type[] types) {
        super((byte) 0x01, 2 + types.length);
        /*
        Bytes.setBytes(bytes, types[0] .getPropertyBytes(), 3);

        if (types == null) return;

        for (int i = 0; i < types.length; i++) {
            Type type = types[i];
            bytes[5 + i] = type.getType();
        }
        */
    }
}
