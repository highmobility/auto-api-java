// TODO: license

package com.highmobility.autoapi;

/**
 * Get all theft alarm properties.
 */
public class GetTheftAlarmState extends GetCommand {
    public GetTheftAlarmState() {
        super(Identifier.THEFT_ALARM);
    }

    GetTheftAlarmState(byte[] bytes) {
        super(bytes);
    }
}