package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.Bytes;

import java.util.Arrays;
import java.util.Enumeration;

class PropertyEnumeration implements Enumeration {
    private int cursor;
    private final byte[] bytes;

    public PropertyEnumeration(byte[] bytes) throws CommandParseException {
        this(bytes, 3);
    }

    public PropertyEnumeration(byte[] bytes, int cursor) throws CommandParseException {
        this.bytes = bytes;
        this.cursor = cursor;
    }

    @Override public boolean hasMoreElements() {
        return cursor + 3 <= bytes.length;
    }

    @Override public EnumeratedProperty nextElement() {
        byte propertyIdentifier = bytes[cursor];
        int propertyStart = cursor + 3;
        int propertySize = 0;

        try {
            propertySize = Property.getUnsignedInt(bytes, cursor + 1, 2);
        } catch (CommandParseException e) {
            e.printStackTrace();
        }

        cursor += 3 + propertySize;
        return new EnumeratedProperty(propertyIdentifier, propertySize, propertyStart);
    }

    class EnumeratedProperty {
        byte identifier;
        int size;
        int valueStart;

        public EnumeratedProperty(byte identifier, int size, int valueStart) {
            this.identifier = identifier;
            this.size = size;
            this.valueStart = valueStart;
        }
    }
}
