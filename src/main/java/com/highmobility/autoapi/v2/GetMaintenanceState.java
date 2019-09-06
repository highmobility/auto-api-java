// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all maintenance properties.
 */
public class GetMaintenanceState extends GetCommand {
    public GetMaintenanceState() {
        super(Identifier.MAINTENANCE, getStateIdentifiers());
    }

    GetMaintenanceState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C };
    }
}