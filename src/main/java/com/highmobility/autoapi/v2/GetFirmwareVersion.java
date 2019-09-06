// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all firmware version properties.
 */
public class GetFirmwareVersion extends GetCommand {
    public GetFirmwareVersion() {
        super(Identifier.FIRMWARE_VERSION, getStateIdentifiers());
    }

    GetFirmwareVersion(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x03 };
    }
}