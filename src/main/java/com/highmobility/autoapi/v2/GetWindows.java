// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all windows properties.
 */
public class GetWindows extends GetCommand {
    public GetWindows() {
        super(Identifier.WINDOWS, getStateIdentifiers());
    }

    GetWindows(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x02, 0x03 };
    }
}