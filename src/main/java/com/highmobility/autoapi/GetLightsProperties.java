// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific lights properties.
 */
public class GetLightsProperties extends GetCommand {
    public GetLightsProperties(byte[] propertyIdentifiers) {
        super(Identifier.LIGHTS, propertyIdentifiers);
    }
}