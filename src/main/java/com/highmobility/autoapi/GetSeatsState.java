package com.highmobility.autoapi;

/**
 * Get the seats state. The car will respond with the Seats State message.
 */
public class GetSeatsState extends Command {
    public static final Type TYPE = new Type(Identifier.SEATS, 0x00);

    public GetSeatsState() {
        super(TYPE);
    }

    GetSeatsState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
