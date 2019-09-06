// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific fueling properties.
 */
public class GetGasFlapStateProperties extends GetCommand {
    public GetGasFlapStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.FUELING, propertyIdentifiers);
    }
}