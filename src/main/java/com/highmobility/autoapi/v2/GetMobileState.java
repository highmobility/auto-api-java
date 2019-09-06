// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all mobile properties.
 */
public class GetMobileState extends GetCommand {
    public GetMobileState() {
        super(Identifier.MOBILE, getStateIdentifiers());
    }

    GetMobileState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01 };
    }
}