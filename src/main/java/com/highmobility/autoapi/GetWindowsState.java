package com.highmobility.autoapi;

/**
 * Get the windows states, which are either open or closed. The car will respond with the Windows
 * State message.
 */
public class GetWindowsState extends Command {
    public static final Type TYPE = new Type(Identifier.WINDOWS, 0x00);

    public GetWindowsState() {
        super(TYPE);
    }

    public GetWindowsState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
