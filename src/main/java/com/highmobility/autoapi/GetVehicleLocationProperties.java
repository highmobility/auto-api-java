// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific vehicle location properties.
 */
public class GetVehicleLocationProperties extends GetCommand {
    public GetVehicleLocationProperties(byte[] propertyIdentifiers) {
        super(Identifier.VEHICLE_LOCATION, propertyIdentifiers);
    }
}