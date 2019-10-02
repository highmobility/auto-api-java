// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific charging properties.
 */
public class GetChargingProperties extends GetCommand {
    public GetChargingProperties(byte[] propertyIdentifiers) {
        super(Identifier.CHARGING, propertyIdentifiers);
    }
}