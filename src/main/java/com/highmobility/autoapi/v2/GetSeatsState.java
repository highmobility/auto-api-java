// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all seats properties.
 */
public class GetSeatsState extends GetCommand {
    public GetSeatsState() {
        super(Identifier.SEATS, getStateIdentifiers());
    }

    GetSeatsState(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x02, 0x03 };
    }
}