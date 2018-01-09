package com.highmobility.autoapi;

/**
 * Get the parking brake state. The car will respond with the Parking Brake message.
 */
public class GetParkingBrakeState extends Command {
    public static final Type TYPE = new Type(Identifier.PARKING_BRAKE, 0x00);

    public GetParkingBrakeState() {
        super(TYPE);
    }

    GetParkingBrakeState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
