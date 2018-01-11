package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum WasherFluidLevel implements HMProperty {
    LOW((byte)0x00),
    FULL((byte)0x01);

    public static WasherFluidLevel fromByte(byte value) throws CommandParseException {
        WasherFluidLevel[] values = WasherFluidLevel.values();

        for (int i = 0; i < values.length; i++) {
            WasherFluidLevel capability = values[i];
            if (capability.getByte() == value) {
                return capability;
            }
        }

        throw new CommandParseException();
    }

    private byte value;
    private Byte identifier;

    WasherFluidLevel(byte value) {
        this.value = value;
    }

    public void setIdentifier(Byte identifier) {
        this.identifier = identifier;
    }

    public byte getByte() {
        return value;
    }

    @Override public byte getPropertyIdentifier() {
        return identifier == null ? 0x01 : identifier;
    }

    @Override public int getPropertyLength() {
        return 1;
    }

    @Override public byte[] getPropertyBytes() {
        return Property.getPropertyBytes(getPropertyIdentifier(), getPropertyLength(), value);
    }
}