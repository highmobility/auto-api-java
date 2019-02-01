package com.highmobility.autoapi.property;

import com.highmobility.value.Bytes;

public class PropertyValueObject implements PropertyValueByteArray {
    Bytes bytes;

    public PropertyValueObject(Bytes bytes) {
        this.bytes = bytes;
    }

    public PropertyValueObject() {

    }

    @Override public Bytes getBytes() {
        return bytes;
    }

    @Override public int getLength() {
        return bytes.getLength();
    }
}
