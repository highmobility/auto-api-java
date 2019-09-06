// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get specific power takeoff properties.
 */
public class GetPowerTakeoffStateProperties extends GetCommand {
    public GetPowerTakeoffStateProperties(byte[] propertyIdentifiers) {
        super(Identifier.POWER_TAKEOFF, propertyIdentifiers);
    }
}