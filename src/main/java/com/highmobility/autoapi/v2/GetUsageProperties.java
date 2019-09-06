// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific usage properties.
 */
public class GetUsageProperties extends GetCommand {
    public GetUsageProperties(byte[] propertyIdentifiers) {
        super(Identifier.USAGE, propertyIdentifiers);
    }
}