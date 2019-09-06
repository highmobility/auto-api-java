// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all trunk properties.
 */
public class GetTrunkState extends GetCommand {
    public GetTrunkState() {
        super(Identifier.TRUNK, getStateIdentifiers());
    }

    GetTrunkState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02 };
    }
}