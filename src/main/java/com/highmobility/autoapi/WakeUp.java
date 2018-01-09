package com.highmobility.autoapi;

/**
 * Wake up the car. This is necessary when the car has fallen asleep, in which case the car responds
 * with the Failure Message to all incoming messages. The car is also waken up by the Lock/Unlock
 * Doors message.
 */
public class WakeUp extends Command {
    public static final Type TYPE = new Type(Identifier.WAKE_UP, 0x02);

    public WakeUp() {
        super(TYPE.getIdentifierAndType(), true);
    }

    WakeUp(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
