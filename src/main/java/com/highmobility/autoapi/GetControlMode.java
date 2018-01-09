package com.highmobility.autoapi;

/**
 * Get the current remote control mode. The car will respond with the Control Mode message.
 */
public class GetControlMode extends Command {
    public static final Type TYPE = new Type(Identifier.REMOTE_CONTROL, 0x00);

    public GetControlMode() {
        super(TYPE);
    }

    GetControlMode(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
