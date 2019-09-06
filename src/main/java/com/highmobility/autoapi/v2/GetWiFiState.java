// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all wi fi properties.
 */
public class GetWiFiState extends GetCommand {
    public GetWiFiState() {
        super(Identifier.WI_FI, getStateIdentifiers());
    }

    GetWiFiState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x03, 0x04 };
    }
}