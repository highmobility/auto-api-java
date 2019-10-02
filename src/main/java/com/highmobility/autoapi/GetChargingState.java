// TODO: license

package com.highmobility.autoapi;

/**
 * Get all charging properties.
 */
public class GetChargingState extends GetCommand {
    public GetChargingState() {
        super(Identifier.CHARGING);
    }

    GetChargingState(byte[] bytes) {
        super(bytes);
    }
}