// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific firmware version properties.
 */
public class GetFirmwareVersionProperties extends GetCommand {
    public GetFirmwareVersionProperties(byte[] propertyIdentifiers) {
        super(Identifier.FIRMWARE_VERSION, propertyIdentifiers);
    }
}