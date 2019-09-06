// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific charging properties.
 */
public class GetChargingStateProperties extends GetCommand {
    public GetChargingStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.CHARGING, propertyIdentifiers);
    }
}