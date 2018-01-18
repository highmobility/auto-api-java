package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * Start or stop charging, which can only be controlled when the car is plugged in. The result is
 * sent through the evented Charge State message.
 */
public class StartStopCharging extends Command {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x02);

    public StartStopCharging (boolean start) {
        super(TYPE.addByte(Property.boolToByte(start)), true);
    }

    StartStopCharging(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
