package com.highmobility.autoapi;

/**
 * Get the diagnostics state of the car. The car will respond with the Diagnostics State message.
 */
public class GetDiagnosticsState extends Command {
    public static final Type TYPE = new Type(Identifier.DIAGNOSTICS, 0x00);

    public GetDiagnosticsState() {
        super(TYPE);
    }

    GetDiagnosticsState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
