// TODO: license
package com.highmobility.autoapi.v2;
/**
 * Get all parking ticket properties.
 */
public class GetParkingTicket extends GetCommand {
    public GetParkingTicket() {
        super(Identifier.PARKING_TICKET, getStateIdentifiers());
    }

    GetParkingTicket(byte[] bytes) {
        super(bytes);
    }

    public static byte[] getStateIdentifiers() {
        return new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05 };
    }
}