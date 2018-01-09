package com.highmobility.autoapi;

/**
 * Get the valet mode, which either activated or not. The car will respond with the Valet Mode
 * message.
 */
public class GetValetMode extends Command {
    public static final Type TYPE = new Type(Identifier.VALET_MODE, 0x00);

    public GetValetMode() {
        super(TYPE);
    }

    GetValetMode(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
