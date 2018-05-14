package com.highmobility.autoapi.property.doors;

import com.highmobility.autoapi.CommandParseException;

/**
 * The door doorLocation
 */
public enum DoorLocation {
    FRONT_LEFT((byte) 0x00),
    FRONT_RIGHT((byte) 0x01),
    REAR_RIGHT((byte) 0x02),
    REAR_LEFT((byte) 0x03);

    public static DoorLocation fromByte(byte value) throws CommandParseException {
        DoorLocation[] allValues = DoorLocation.values();

        for (int i = 0; i < allValues.length; i++) {
            DoorLocation value1 = allValues[i];
            if (value1.getByte() == value) {
                return value1;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    DoorLocation(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }
}