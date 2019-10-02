// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific doors properties.
 */
public class GetDoorsProperties extends GetCommand {
    public GetDoorsProperties(byte[] propertyIdentifiers) {
        super(Identifier.DOORS, propertyIdentifiers);
    }
}