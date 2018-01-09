package com.highmobility.autoapi;

/**
 * Get the chassis settings. The car will respond with the Chassis Settings message.
 */
public class GetChassisSettings extends Command {
    public static final Type TYPE = new Type(Identifier.CHASSIS_SETTINGS, 0x00);

    public GetChassisSettings() {
        super(TYPE);
    }

    GetChassisSettings(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
