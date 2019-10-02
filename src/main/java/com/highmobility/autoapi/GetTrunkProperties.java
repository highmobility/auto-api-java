// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific trunk properties.
 */
public class GetTrunkProperties extends GetCommand {
    public GetTrunkProperties(byte[] propertyIdentifiers) {
        super(Identifier.TRUNK, propertyIdentifiers);
    }
}