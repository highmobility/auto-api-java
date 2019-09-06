// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific wi fi properties.
 */
public class GetWiFiStateProperties extends GetCommand {
    public GetWiFiStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.WI_FI, propertyIdentifiers);
    }
}