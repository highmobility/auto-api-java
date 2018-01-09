package com.highmobility.autoapi;

/**
 * Get the parking ticket. The car will respond with the Parking Ticket message.
 */
public class GetParkingTicket extends Command {
    public static final Type TYPE = new Type(Identifier.PARKING_TICKET, 0x00);

    public GetParkingTicket() {
        super(TYPE);
    }

    GetParkingTicket(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
