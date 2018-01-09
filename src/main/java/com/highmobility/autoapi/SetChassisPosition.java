package com.highmobility.autoapi;

/**
 * Set the chassis position. The result is sent through the Chassis Settings message.
 */
public class SetChassisPosition extends Command {
    public static final Type TYPE = new Type(Identifier.CHASSIS_SETTINGS, 0x05);

    /**
     *
     * @param position The chassis position in mm calculated from the lowest point
     */
    public SetChassisPosition(Integer position) {
        super(TYPE.addByte(position.byteValue()), true);
    }

    SetChassisPosition(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
