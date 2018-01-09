package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum ParkingTicketState implements HMProperty {
    ENDED((byte)0x00),
    STARTED((byte)0x01);

    public static ParkingTicketState fromByte(byte byteValue) throws CommandParseException {
        ParkingTicketState[] values = ParkingTicketState.values();

        for (int i = 0; i < values.length; i++) {
            ParkingTicketState state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    ParkingTicketState(byte value) {
        this.value = value;
    }

    public byte getByte() {
        return value;
    }

    @Override public byte getPropertyIdentifier() {
        return 0x01;
    }

    @Override public int getPropertyLength() {
        return 1;
    }

    @Override public byte[] getPropertyBytes() {
        return Property.getPropertyBytes(getPropertyIdentifier(), getPropertyLength(), value);
    }
}