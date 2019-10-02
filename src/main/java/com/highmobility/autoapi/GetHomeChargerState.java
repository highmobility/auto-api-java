// TODO: license

package com.highmobility.autoapi;

/**
 * Get all home charger properties.
 */
public class GetHomeChargerState extends GetCommand {
    public GetHomeChargerState() {
        super(Identifier.HOME_CHARGER);
    }

    GetHomeChargerState(byte[] bytes) {
        super(bytes);
    }
}