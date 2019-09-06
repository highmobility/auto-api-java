// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all tachograph properties.
 */
public class GetTachographState extends GetCommand {
    public GetTachographState() {
        super(Identifier.TACHOGRAPH, getStateIdentifiers());
    }

    GetTachographState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
    }
}