package com.highmobility.autoapi;

/**
 * Get the rooftop state. The car will respond with the Rooftop State message.
 */
public class GetRooftopState extends Command {
    public static final Type TYPE = new Type(Identifier.ROOFTOP, 0x00);

    public GetRooftopState() {
        super(TYPE);
    }

    GetRooftopState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
