package com.highmobility.autoapi;

/**
 * Get the windsreen state. The car will respond with the Windscreen State message.
 */
public class GetWindscreenState extends Command {
    public static final Type TYPE = new Type(Identifier.WINDSCREEN, 0x00);

    public GetWindscreenState() {
        super(TYPE);
    }

    public GetWindscreenState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
