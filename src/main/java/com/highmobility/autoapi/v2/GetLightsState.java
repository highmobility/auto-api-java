// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all lights properties.
 */
public class GetLightsState extends GetCommand {
    public GetLightsState() {
        super(Identifier.LIGHTS, getStateIdentifiers());
    }

    GetLightsState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09 };
    }
}