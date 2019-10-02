// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific offroad properties.
 */
public class GetOffroadProperties extends GetCommand {
    public GetOffroadProperties(byte[] propertyIdentifiers) {
        super(Identifier.OFFROAD, propertyIdentifiers);
    }
}