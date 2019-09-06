// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific lights properties.
 */
public class GetLightsStateProperties extends GetCommand {
    public GetLightsStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.LIGHTS, propertyIdentifiers);
    }
}