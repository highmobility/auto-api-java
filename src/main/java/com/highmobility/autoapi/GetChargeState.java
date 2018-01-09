package com.highmobility.autoapi;

/**
 * Get the charge state. The car will respond with the Charge State message.
 */
public class GetChargeState extends Command {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x00);

    public GetChargeState() {
        super(TYPE);
    }

    GetChargeState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
