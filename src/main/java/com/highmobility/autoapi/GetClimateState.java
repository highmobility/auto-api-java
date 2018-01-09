package com.highmobility.autoapi;

/**
 * Get the climate state. The car will respond with the Climate State message.
 */
public class GetClimateState extends Command {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x00);

    public GetClimateState() {
        super(TYPE);
    }

    GetClimateState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
