package com.highmobility.autoapi;

/**
 * Open the gas flap of the car. This is possible even if the car is locked.
 */
public class OpenGasFlap extends Command {
    public static final Type TYPE = new Type(Identifier.FUELING, 0x02);

    public OpenGasFlap() throws CommandParseException {
        super(TYPE);
    }

    OpenGasFlap(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length != 4) throw new CommandParseException();
    }
}
