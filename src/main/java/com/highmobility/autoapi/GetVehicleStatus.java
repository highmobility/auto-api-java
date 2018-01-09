package com.highmobility.autoapi;

/**
 * Get the vehicle status. The car will respond with the Vehicle Status message.
 */
public class GetVehicleStatus extends Command {
    public static final Type TYPE = new Type(Identifier.VEHICLE_STATUS, 0x00);

    public GetVehicleStatus() {
        super(TYPE);
    }

    GetVehicleStatus(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
