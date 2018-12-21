package com.highmobility.autoapi.property.value;

import com.highmobility.autoapi.CommandParseException;

public enum Location {
    FRONT_LEFT((byte) 0x00),
    FRONT_RIGHT((byte) 0x01),
    REAR_RIGHT((byte) 0x02),
    REAR_LEFT((byte) 0x03),
    HATCH((byte) 0x04);

    public static Location fromByte(byte value) throws CommandParseException {
        Location[] allValues = Location.values();

        for (int i = 0; i < allValues.length; i++) {
            Location value1 = allValues[i];
            if (value1.getByte() == value) {
                return value1;
            }
        }

        throw new CommandParseException();
    }

    private byte value;
    private byte identifier;

    Location(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }

    public byte getIdentifier() {
        return identifier;
    }

    public Location setIdentifier(byte identifier) {
        this.identifier = identifier;
        return this;
    }
}
