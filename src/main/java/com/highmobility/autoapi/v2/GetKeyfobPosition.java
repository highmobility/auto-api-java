// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all keyfob position properties.
 */
public class GetKeyfobPosition extends GetCommand {
    public GetKeyfobPosition() {
        super(Identifier.KEYFOB_POSITION, getStateIdentifiers());
    }

    GetKeyfobPosition(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01 };
    }
}