// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all usage properties.
 */
public class GetUsage extends GetCommand {
    public GetUsage() {
        super(Identifier.USAGE, getStateIdentifiers());
    }

    GetUsage(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };
    }
}