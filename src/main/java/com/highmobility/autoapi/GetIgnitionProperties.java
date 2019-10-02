// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific ignition properties.
 */
public class GetIgnitionProperties extends GetCommand {
    public GetIgnitionProperties(byte[] propertyIdentifiers) {
        super(Identifier.IGNITION, propertyIdentifiers);
    }
}