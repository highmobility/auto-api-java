// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific rooftop control properties.
 */
public class GetRooftopProperties extends GetCommand {
    public GetRooftopProperties(byte[] propertyIdentifiers) {
        super(Identifier.ROOFTOP_CONTROL, propertyIdentifiers);
    }
}