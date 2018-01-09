package com.highmobility.autoapi;

/**
 * Get the light conditions. The car will respond with the Light Conditions message.
 */
public class GetLightConditions extends Command {
    public static final Type TYPE = new Type(Identifier.LIGHT_CONDITIONS, 0x00);

    public GetLightConditions() {
        super(TYPE);
    }

    GetLightConditions(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
