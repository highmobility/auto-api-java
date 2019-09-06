// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all offroad properties.
 */
public class GetOffroadState extends GetCommand {
    public GetOffroadState() {
        super(Identifier.OFFROAD, getStateIdentifiers());
    }

    GetOffroadState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02 };
    }
}