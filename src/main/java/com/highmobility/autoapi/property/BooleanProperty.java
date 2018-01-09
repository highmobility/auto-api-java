package com.highmobility.autoapi.property;

public class BooleanProperty extends Property {
    public BooleanProperty(byte identifier, boolean value) {
        super(identifier, 1);
        bytes[3] = Property.boolToByte(value);
    }
}
