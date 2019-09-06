// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all vehicle location properties.
 */
public class GetVehicleLocation extends GetCommand {
    public GetVehicleLocation() {
        super(Identifier.VEHICLE_LOCATION, getStateIdentifiers());
    }

    GetVehicleLocation(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x04, 0x05, 0x06 };
    }
}