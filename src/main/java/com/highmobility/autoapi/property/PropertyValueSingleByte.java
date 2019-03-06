package com.highmobility.autoapi.property;

public interface PropertyValueSingleByte extends IPropertyValue {
    byte getByte();

    @Override default int getLength() {
        return 1;
    }
}
