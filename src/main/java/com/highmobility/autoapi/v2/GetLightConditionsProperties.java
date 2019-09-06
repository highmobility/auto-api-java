// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific light conditions properties.
 */
public class GetLightConditionsProperties extends GetCommand {
    public GetLightConditionsProperties(byte[] propertyIdentifiers) {
        super(Identifier.LIGHT_CONDITIONS, propertyIdentifiers);
    }
}