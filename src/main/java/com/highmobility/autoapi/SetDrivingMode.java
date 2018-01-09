package com.highmobility.autoapi;

import com.highmobility.autoapi.property.DrivingMode;

/**
 * Set the driving mode. The result is sent through the Chassis Settings message.
 */
public class SetDrivingMode extends Command {
    public static final Type TYPE = new Type(Identifier.CHASSIS_SETTINGS, 0x02);

    public SetDrivingMode(DrivingMode drivingMode) {
        super(TYPE.addByte(drivingMode.getByte()), true);
    }

    SetDrivingMode(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
