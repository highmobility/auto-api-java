// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all driver fatigue properties.
 */
public class GetDriverFatigueState extends GetCommand {
    public GetDriverFatigueState() {
        super(Identifier.DRIVER_FATIGUE, getStateIdentifiers());
    }

    GetDriverFatigueState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01 };
    }
}