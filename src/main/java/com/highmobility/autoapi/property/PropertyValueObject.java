package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.value.Bytes;

public class PropertyValueObject implements PropertyValueByteArray {
    Bytes bytes;

    public PropertyValueObject(Bytes value) throws CommandParseException {
        update(value);
    }

    public PropertyValueObject() {

    }

    @Override public Bytes getBytes() {
        return bytes;
    }

    @Override public int getLength() {
        return bytes.getLength();
    }

    public void update(Bytes value) throws CommandParseException {
        this.bytes = value;
    }
}
