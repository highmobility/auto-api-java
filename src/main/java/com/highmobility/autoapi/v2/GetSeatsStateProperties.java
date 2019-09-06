// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific seats properties.
 */
public class GetSeatsStateProperties extends GetCommand {
    public GetSeatsStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.SEATS, propertyIdentifiers);
    }
}