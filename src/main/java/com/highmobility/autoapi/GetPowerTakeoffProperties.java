// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific power takeoff properties.
 */
public class GetPowerTakeoffProperties extends GetCommand {
    public GetPowerTakeoffProperties(byte[] propertyIdentifiers) {
        super(Identifier.POWER_TAKEOFF, propertyIdentifiers);
    }
}