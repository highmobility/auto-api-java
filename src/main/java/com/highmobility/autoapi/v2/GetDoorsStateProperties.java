// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific doors properties.
 */
public class GetDoorsStateProperties extends GetCommand {
    public GetDoorsStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.DOORS, propertyIdentifiers);
    }
}