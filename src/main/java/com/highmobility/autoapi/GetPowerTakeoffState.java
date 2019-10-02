// TODO: license

package com.highmobility.autoapi;

/**
 * Get all power takeoff properties.
 */
public class GetPowerTakeoffState extends GetCommand {
    public GetPowerTakeoffState() {
        super(Identifier.POWER_TAKEOFF);
    }

    GetPowerTakeoffState(byte[] bytes) {
        super(bytes);
    }
}