// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific honk horn flash lights properties.
 */
public class GetFlashersProperties extends GetCommand {
    public GetFlashersProperties(byte[] propertyIdentifiers) {
        super(Identifier.HONK_HORN_FLASH_LIGHTS, propertyIdentifiers);
    }
}