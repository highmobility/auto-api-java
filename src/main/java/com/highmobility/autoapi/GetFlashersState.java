package com.highmobility.autoapi;

/**
 * Get the state of the flashers. The car will respond with the Flashers State message.
 */
public class GetFlashersState extends Command {
    public static final Type TYPE = new Type(Identifier.HONK_FLASH, 0x00);

    public GetFlashersState() {
        super(TYPE);
    }

    public GetFlashersState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
