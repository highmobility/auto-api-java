// TODO: license
package com.highmobility.autoapi;
/**
 * Get specific chassis settings properties.
 */
public class GetChassisSettingsProperties extends GetCommand {
    public GetChassisSettingsProperties(byte[] propertyIdentifiers) {
        super(Identifier.CHASSIS_SETTINGS, propertyIdentifiers);
    }
}