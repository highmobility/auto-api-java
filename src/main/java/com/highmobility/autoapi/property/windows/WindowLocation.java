package com.highmobility.autoapi.property.windows;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;

public enum WindowLocation {
    FRONT_LEFT((byte) 0x00),
    FRONT_RIGHT((byte) 0x01),
    REAR_RIGHT((byte) 0x02),
    REAR_LEFT((byte) 0x03),
    HATCH((byte) 0x04);

    public static WindowLocation fromByte(byte value) throws CommandParseException {
        WindowLocation[] allValues = WindowLocation.values();

        for (int i = 0; i < allValues.length; i++) {
            WindowLocation value1 = allValues[i];
            if (value1.getByte() == value) {
                return value1;
            }
        }

        throw new CommandParseException();
    }

    private byte value;
    private byte identifier;

    WindowLocation(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }

    public byte getIdentifier() {
        return identifier;
    }

    public WindowLocation setIdentifier(byte identifier) {
        this.identifier = identifier;
        return this;
    }
}
