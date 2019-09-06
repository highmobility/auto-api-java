// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific rooftop control properties.
 */
public class GetRooftopStateProperties extends GetCommand {
    public GetRooftopStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.ROOFTOP_CONTROL, propertyIdentifiers);
    }
}