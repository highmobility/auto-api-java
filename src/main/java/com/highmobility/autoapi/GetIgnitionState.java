package com.highmobility.autoapi;

/**
 * Get the ignition state, which is either on or off. The car will respond with the Ignition State
 * message.
 */
public class GetIgnitionState extends Command {
    public static final Type TYPE = new Type(Identifier.ENGINE, 0x00);

    public GetIgnitionState() {
        super(TYPE);
    }

    GetIgnitionState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
