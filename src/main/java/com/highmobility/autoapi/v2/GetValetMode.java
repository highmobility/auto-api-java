// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all valet mode properties.
 */
public class GetValetMode extends GetCommand {
    public GetValetMode() {
        super(Identifier.VALET_MODE, getStateIdentifiers());
    }

    GetValetMode(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01 };
    }
}