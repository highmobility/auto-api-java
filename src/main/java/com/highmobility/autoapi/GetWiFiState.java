// TODO: license

package com.highmobility.autoapi;

/**
 * Get all wi fi properties.
 */
public class GetWiFiState extends GetCommand {
    public GetWiFiState() {
        super(Identifier.WI_FI);
    }

    GetWiFiState(byte[] bytes) {
        super(bytes);
    }
}