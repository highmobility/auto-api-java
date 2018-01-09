package com.highmobility.autoapi;

/**
 * Get the lights state. The car will respond with the Lights State message.
 */
public class GetLightsState extends Command {
    public static final Type TYPE = new Type(Identifier.LIGHTS, 0x00);

    public GetLightsState() {
        super(TYPE);
    }

    GetLightsState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
