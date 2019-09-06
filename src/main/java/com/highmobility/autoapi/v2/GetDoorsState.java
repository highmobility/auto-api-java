// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all doors properties.
 */
public class GetDoorsState extends GetCommand {
    public GetDoorsState() {
        super(Identifier.DOORS, getStateIdentifiers());
    }

    GetDoorsState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x02, 0x03, 0x04, 0x05, 0x06 };
    }
}