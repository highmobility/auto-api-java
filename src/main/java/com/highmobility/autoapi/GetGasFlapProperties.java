// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific fueling properties.
 */
public class GetGasFlapProperties extends GetCommand {
    public GetGasFlapProperties(byte[] propertyIdentifiers) {
        super(Identifier.FUELING, propertyIdentifiers);
    }
}