// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all ignition properties.
 */
public class GetIgnitionState extends GetCommand {
    public GetIgnitionState() {
        super(Identifier.IGNITION, getStateIdentifiers());
    }

    GetIgnitionState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02 };
    }
}