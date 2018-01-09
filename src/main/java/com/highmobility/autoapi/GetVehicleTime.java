package com.highmobility.autoapi;

/**
 * Get the vehicle status. The car will respond with the Vehicle Status message.
 */
public class GetVehicleTime extends Command {
    public static final Type TYPE = new Type(Identifier.VEHICLE_TIME, 0x00);

    public GetVehicleTime() {
        super(TYPE);
    }

    GetVehicleTime(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
