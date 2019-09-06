// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all cruise control properties.
 */
public class GetCruiseControlState extends GetCommand {
    public GetCruiseControlState() {
        super(Identifier.CRUISE_CONTROL, getStateIdentifiers());
    }

    GetCruiseControlState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05 };
    }
}