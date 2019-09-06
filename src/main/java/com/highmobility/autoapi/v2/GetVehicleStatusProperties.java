// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific vehicle status properties.
 */
public class GetVehicleStatusProperties extends GetCommand {
    public GetVehicleStatusProperties(byte[] propertyIdentifiers) {
        super(Identifier.VEHICLE_STATUS, propertyIdentifiers);
    }
}