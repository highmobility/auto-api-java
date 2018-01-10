package com.highmobility.autoapi;

import com.highmobility.autoapi.property.DoorLockProperty;

/**
 * Attempt to lock or unlock all doors of the car. The result is not received by the ack but instead
 * sent through the evented Lock State message with either the mode 0x00 Unlocked or 0x01 Locked.
 */
public class LockUnlockDoors extends Command {
    public static final Type TYPE = new Type(Identifier.DOOR_LOCKS, 0x02);

    public LockUnlockDoors(DoorLockProperty.LockState state) {
        super(TYPE.addByte(state.getByte()), true);
    }

    LockUnlockDoors(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length != 4) throw new CommandParseException();
    }
}
