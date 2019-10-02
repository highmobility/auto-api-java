// TODO: license

package com.highmobility.autoapi;

/**
 * Get all vehicle time properties.
 */
public class GetVehicleTime extends GetCommand {
    public GetVehicleTime() {
        super(Identifier.VEHICLE_TIME);
    }

    GetVehicleTime(byte[] bytes) {
        super(bytes);
    }
}