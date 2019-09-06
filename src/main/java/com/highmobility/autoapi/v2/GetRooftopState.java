// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all rooftop control properties.
 */
public class GetRooftopState extends GetCommand {
    public GetRooftopState() {
        super(Identifier.ROOFTOP_CONTROL, getStateIdentifiers());
    }

    GetRooftopState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05 };
    }
}