package com.highmobility.autoapi;


/**
 * Get the lock state, which either locked or unlocked. The car will respond with the Lock State
 * message.
 */
public class GetLockState extends Command {
    public static final Type TYPE = new Type(Identifier.DOOR_LOCKS, 0x00);

    public GetLockState() {
        super(TYPE);
    }

    GetLockState(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
