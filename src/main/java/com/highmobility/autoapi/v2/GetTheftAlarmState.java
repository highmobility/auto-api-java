// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all theft alarm properties.
 */
public class GetTheftAlarmState extends GetCommand {
    public GetTheftAlarmState() {
        super(Identifier.THEFT_ALARM, getStateIdentifiers());
    }

    GetTheftAlarmState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01 };
    }
}