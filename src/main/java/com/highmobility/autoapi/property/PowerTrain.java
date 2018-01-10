package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum PowerTrain implements HMProperty {
    UNKNOWN((byte) 0x00),
    ALLELECTRIC((byte) 0x01),
    COMBUSTIONENGINE((byte) 0x02),
    PLUGINHYBRID((byte) 0x03),
    HYDROGEN((byte) 0x04),
    HYDROGENHYBRID((byte) 0x05);

    public static PowerTrain fromByte(byte value) throws CommandParseException {
        PowerTrain[] values = PowerTrain.values();

        for (int i = 0; i < values.length; i++) {
            PowerTrain capability = values[i];
            if (capability.getByte() == value) {
                return capability;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    PowerTrain(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }


    @Override public byte getPropertyIdentifier() {
        return 0x02;
    }

    @Override public int getPropertyLength() {
        return 1;
    }

    @Override public byte[] getPropertyBytes() {
        return Property.getPropertyBytes(getPropertyIdentifier(), getPropertyLength(), value);
    }
}