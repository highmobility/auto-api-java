// TODO: license

package com.highmobility.autoapi;

/**
 * Get all parking brake properties.
 */
public class GetParkingBrakeState extends GetCommand {
    public GetParkingBrakeState() {
        super(Identifier.PARKING_BRAKE);
    }

    GetParkingBrakeState(byte[] bytes) {
        super(bytes);
    }
}