package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.value.Bytes;

public class PropertyValueObject extends Bytes {
    public PropertyValueObject(Bytes value) throws CommandParseException {
        update(value);
    }

    public PropertyValueObject() {

    }

    public PropertyValueObject(int bytesLength) {
        super(bytesLength);
    }

    public void update(Bytes value) throws CommandParseException {
        this.bytes = value.getByteArray();
    }
}
