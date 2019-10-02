// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific wi fi properties.
 */
public class GetWiFiProperties extends GetCommand {
    public GetWiFiProperties(byte[] propertyIdentifiers) {
        super(Identifier.WI_FI, propertyIdentifiers);
    }
}