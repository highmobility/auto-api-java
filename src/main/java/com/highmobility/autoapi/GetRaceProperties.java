// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific race properties.
 */
public class GetRaceProperties extends GetCommand {
    public GetRaceProperties(byte[] propertyIdentifiers) {
        super(Identifier.RACE, propertyIdentifiers);
    }
}