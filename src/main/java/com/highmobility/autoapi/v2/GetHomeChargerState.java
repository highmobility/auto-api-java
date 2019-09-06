// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all home charger properties.
 */
public class GetHomeChargerState extends GetCommand {
    public GetHomeChargerState() {
        super(Identifier.HOME_CHARGER, getStateIdentifiers());
    }

    GetHomeChargerState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x08, 0x09, 0x0a, 0x0b, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12 };
    }
}