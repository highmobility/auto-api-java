// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific home charger properties.
 */
public class GetHomeChargerProperties extends GetCommand {
    public GetHomeChargerProperties(byte[] propertyIdentifiers) {
        super(Identifier.HOME_CHARGER, propertyIdentifiers);
    }
}