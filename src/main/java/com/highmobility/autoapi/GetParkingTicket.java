// TODO: license

package com.highmobility.autoapi;

/**
 * Get all parking ticket properties.
 */
public class GetParkingTicket extends GetCommand {
    public GetParkingTicket() {
        super(Identifier.PARKING_TICKET);
    }

    GetParkingTicket(byte[] bytes) {
        super(bytes);
    }
}