package com.highmobility.autoapi;

/**
 * Get the offroad state. The car will respond with the Offroad State message.
 */
public class GetOffroadState extends Command {
    public static final Type TYPE = new Type(Identifier.OFF_ROAD, 0x00);

    public GetOffroadState() {
        super(TYPE.getIdentifierAndType(), true);
    }

    GetOffroadState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}