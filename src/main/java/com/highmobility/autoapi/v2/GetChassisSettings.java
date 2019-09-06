// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all chassis settings properties.
 */
public class GetChassisSettings extends GetCommand {
    public GetChassisSettings() {
        super(Identifier.CHASSIS_SETTINGS, getStateIdentifiers());
    }

    GetChassisSettings(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a };
    }
}