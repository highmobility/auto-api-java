// TODO: license

package com.highmobility.autoapi;

/**
 * Get all vehicle location properties.
 */
public class GetVehicleLocation extends GetCommand {
    public GetVehicleLocation() {
        super(Identifier.VEHICLE_LOCATION);
    }

    GetVehicleLocation(byte[] bytes) {
        super(bytes);
    }
}