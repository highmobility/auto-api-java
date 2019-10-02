// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific seats properties.
 */
public class GetSeatsProperties extends GetCommand {
    public GetSeatsProperties(byte[] propertyIdentifiers) {
        super(Identifier.SEATS, propertyIdentifiers);
    }
}