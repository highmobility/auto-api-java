// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific maintenance properties.
 */
public class GetMaintenanceStateProperties extends GetCommand {
    public GetMaintenanceStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.MAINTENANCE, propertyIdentifiers);
    }
}