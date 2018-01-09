package com.highmobility.autoapi;

import com.highmobility.utils.Bytes;

/**
 * This activates or deactivates the emergency flashers of the car, typically the blinkers to alarm
 * other drivers.
 */
public class ActivateDeactivateEmergencyFlasher extends Command {
    public static final Type TYPE = new Type(Identifier.HONK_FLASH, 0x03);

    public ActivateDeactivateEmergencyFlasher(boolean activate) {
        super(TYPE.addByte(Bytes.boolToByte(activate)), true);
    }

    ActivateDeactivateEmergencyFlasher(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
