// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific climate properties.
 */
public class GetClimateProperties extends GetCommand {
    public GetClimateProperties(byte[] propertyIdentifiers) {
        super(Identifier.CLIMATE, propertyIdentifiers);
    }
}