// TODO: license

package com.highmobility.autoapi;

/**
 * Get all maintenance properties.
 */
public class GetMaintenanceState extends GetCommand {
    public GetMaintenanceState() {
        super(Identifier.MAINTENANCE);
    }

    GetMaintenanceState(byte[] bytes) {
        super(bytes);
    }
}