package com.highmobility.autoapi.property;

public interface HMProperty {
    byte getPropertyIdentifier();
    int getPropertyLength();
    byte[] getPropertyBytes();
}
