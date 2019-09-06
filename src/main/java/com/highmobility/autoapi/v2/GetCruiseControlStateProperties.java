// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific cruise control properties.
 */
public class GetCruiseControlStateProperties extends GetCommand {
    public GetCruiseControlStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.CRUISE_CONTROL, propertyIdentifiers);
    }
}