package com.highmobility.autoapi;

/**
 * End parking. This updates the parking ticket information. If no end date was set, the current
 * time is set as the ending time. The result is sent through the evented Parking Ticket message.
 */
public class EndParking extends Command {
    public static final Type TYPE = new Type(Identifier.PARKING_TICKET, 0x03);
    // 004703
    public EndParking() {
        super(TYPE);
    }

    EndParking(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
