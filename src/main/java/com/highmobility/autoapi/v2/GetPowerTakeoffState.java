// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all power takeoff properties.
 */
public class GetPowerTakeoffState extends GetCommand {
    public GetPowerTakeoffState() {
        super(Identifier.POWER_TAKEOFF, getStateIdentifiers());
    }

    GetPowerTakeoffState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02 };
    }
}