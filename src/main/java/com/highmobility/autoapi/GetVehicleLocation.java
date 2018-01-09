package com.highmobility.autoapi;

/**
 * Get the vehicle location, which will return the latest recorded coordinates of the car. The car
 * will respond with the Vehicle Location message.
 */
public class GetVehicleLocation extends Command {
    public static final Type TYPE = new Type(Identifier.VEHICLE_LOCATION, 0x00);

    public GetVehicleLocation() {
        super(TYPE);
    }

    GetVehicleLocation(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
