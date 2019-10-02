// TODO: license

package com.highmobility.autoapi;

/**
 * Get all vehicle status properties.
 */
public class GetVehicleStatus extends GetCommand {
    public GetVehicleStatus() {
        super(Identifier.VEHICLE_STATUS);
    }

    GetVehicleStatus(byte[] bytes) {
        super(bytes);
    }
}