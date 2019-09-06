// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all fueling properties.
 */
public class GetGasFlapState extends GetCommand {
    public GetGasFlapState() {
        super(Identifier.FUELING, getStateIdentifiers());
    }

    GetGasFlapState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x02, 0x03 };
    }
}