// TODO: license

package com.highmobility.autoapi;

/**
 * Get all chassis settings properties.
 */
public class GetChassisSettings extends GetCommand {
    public GetChassisSettings() {
        super(Identifier.CHASSIS_SETTINGS);
    }

    GetChassisSettings(byte[] bytes) {
        super(bytes);
    }
}