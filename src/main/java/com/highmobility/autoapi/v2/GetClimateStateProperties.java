// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific climate properties.
 */
public class GetClimateStateProperties extends GetCommand {
    public GetClimateStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.CLIMATE, propertyIdentifiers);
    }
}