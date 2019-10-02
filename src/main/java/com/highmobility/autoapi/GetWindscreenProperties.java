// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific windscreen properties.
 */
public class GetWindscreenProperties extends GetCommand {
    public GetWindscreenProperties(byte[] propertyIdentifiers) {
        super(Identifier.WINDSCREEN, propertyIdentifiers);
    }
}