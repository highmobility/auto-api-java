// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all capabilities properties.
 */
public class GetCapabilities extends GetCommand {
    public GetCapabilities() {
        super(Identifier.CAPABILITIES, getStateIdentifiers());
    }

    GetCapabilities(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01 };
    }
}