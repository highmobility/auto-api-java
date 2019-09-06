// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific race properties.
 */
public class GetRaceStateProperties extends GetCommand {
    public GetRaceStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.RACE, propertyIdentifiers);
    }
}