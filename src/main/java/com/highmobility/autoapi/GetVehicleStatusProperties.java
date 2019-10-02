// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific vehicle status properties.
 */
public class GetVehicleStatusProperties extends GetCommand {
    public GetVehicleStatusProperties(byte[] propertyIdentifiers) {
        super(Identifier.VEHICLE_STATUS, propertyIdentifiers);
    }
}