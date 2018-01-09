package com.highmobility.autoapi;

/**
 * Get the race state. The car will respond with the Race State message.
 */
public class GetRaceState extends Command {
    public static final Type TYPE = new Type(Identifier.RACE, 0x00);

    public GetRaceState() {
        super(TYPE);
    }

    GetRaceState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
