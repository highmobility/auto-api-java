// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all hood properties.
 */
public class GetHoodState extends GetCommand {
    public GetHoodState() {
        super(Identifier.HOOD, getStateIdentifiers());
    }

    GetHoodState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01 };
    }
}