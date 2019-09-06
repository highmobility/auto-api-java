// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all vehicle time properties.
 */
public class GetVehicleTime extends GetCommand {
    public GetVehicleTime() {
        super(Identifier.VEHICLE_TIME, getStateIdentifiers());
    }

    GetVehicleTime(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01 };
    }
}