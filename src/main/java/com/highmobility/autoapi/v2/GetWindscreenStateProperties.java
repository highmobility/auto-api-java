// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific windscreen properties.
 */
public class GetWindscreenStateProperties extends GetCommand {
    public GetWindscreenStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.WINDSCREEN, propertyIdentifiers);
    }
}