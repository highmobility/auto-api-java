// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific cruise control properties.
 */
public class GetCruiseControlProperties extends GetCommand {
    public GetCruiseControlProperties(byte[] propertyIdentifiers) {
        super(Identifier.CRUISE_CONTROL, propertyIdentifiers);
    }
}