// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific trunk properties.
 */
public class GetTrunkStateProperties extends GetCommand {
    public GetTrunkStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.TRUNK, propertyIdentifiers);
    }
}