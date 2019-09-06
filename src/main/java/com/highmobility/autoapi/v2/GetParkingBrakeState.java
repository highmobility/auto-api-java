// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all parking brake properties.
 */
public class GetParkingBrakeState extends GetCommand {
    public GetParkingBrakeState() {
        super(Identifier.PARKING_BRAKE, getStateIdentifiers());
    }

    GetParkingBrakeState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01 };
    }
}