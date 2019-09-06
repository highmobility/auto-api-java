// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific offroad properties.
 */
public class GetOffroadStateProperties extends GetCommand {
    public GetOffroadStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.OFFROAD, propertyIdentifiers);
    }
}