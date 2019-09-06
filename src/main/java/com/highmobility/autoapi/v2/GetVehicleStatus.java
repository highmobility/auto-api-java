// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all vehicle status properties.
 */
public class GetVehicleStatus extends GetCommand {
    public GetVehicleStatus() {
        super(Identifier.VEHICLE_STATUS, getStateIdentifiers());
    }

    GetVehicleStatus(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, (byte)0x99 };
    }
}