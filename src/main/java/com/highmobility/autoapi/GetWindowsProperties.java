// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific windows properties.
 */
public class GetWindowsProperties extends GetCommand {
    public GetWindowsProperties(byte[] propertyIdentifiers) {
        super(Identifier.WINDOWS, propertyIdentifiers);
    }
}