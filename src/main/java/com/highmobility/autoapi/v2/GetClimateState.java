// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all climate properties.
 */
public class GetClimateState extends GetCommand {
    public GetClimateState() {
        super(Identifier.CLIMATE, getStateIdentifiers());
    }

    GetClimateState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0b, 0x0c };
    }
}