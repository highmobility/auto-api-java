// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all windscreen properties.
 */
public class GetWindscreenState extends GetCommand {
    public GetWindscreenState() {
        super(Identifier.WINDSCREEN, getStateIdentifiers());
    }

    GetWindscreenState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08 };
    }
}