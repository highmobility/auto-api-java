// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific maintenance properties.
 */
public class GetMaintenanceProperties extends GetCommand {
    public GetMaintenanceProperties(byte[] propertyIdentifiers) {
        super(Identifier.MAINTENANCE, propertyIdentifiers);
    }
}