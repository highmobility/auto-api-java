// TODO: license

package com.highmobility.autoapi;

/**
 * Get all firmware version properties.
 */
public class GetFirmwareVersion extends GetCommand {
    public GetFirmwareVersion() {
        super(Identifier.FIRMWARE_VERSION);
    }

    GetFirmwareVersion(byte[] bytes) {
        super(bytes);
    }
}