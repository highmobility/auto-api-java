// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific home charger properties.
 */
public class GetHomeChargerStateProperties extends GetCommand {
    public GetHomeChargerStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.HOME_CHARGER, propertyIdentifiers);
    }
}