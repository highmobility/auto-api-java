package com.highmobility.autoapi;

/**
 * Get the gas flap state. The car will respond with the Gas Flap message.
 */
public class GetGasFlapState extends Command {
    public static final Type TYPE = new Type(Identifier.FUELING, 0x00);

    public GetGasFlapState() {
        super(TYPE);
    }

    GetGasFlapState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
