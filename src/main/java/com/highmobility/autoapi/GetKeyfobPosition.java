package com.highmobility.autoapi;

/**
 * Get the keyfob position relative to the car. The car will respond with the Keyfob Position
 * message.
 */
public class GetKeyfobPosition extends Command {
    public static final Type TYPE = new Type(Identifier.KEYFOB_POSITION, 0x00);

    public GetKeyfobPosition() {
        super(TYPE);
    }

    GetKeyfobPosition(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
