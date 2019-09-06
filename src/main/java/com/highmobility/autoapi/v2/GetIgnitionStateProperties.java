// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific ignition properties.
 */
public class GetIgnitionStateProperties extends GetCommand {
    public GetIgnitionStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.IGNITION, propertyIdentifiers);
    }
}