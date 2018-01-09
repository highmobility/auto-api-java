package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * Turn on or off the parking brake. The result is sent through the evented Parking Brake State
 * message.
 */
public class ActivateInactivateParkingBrake extends Command {
    public static final Type TYPE = new Type(Identifier.PARKING_BRAKE, 0x02);

    /**
     *
     * @param activate Boolean indicating whether to activate parking brake.
     */
    public ActivateInactivateParkingBrake(boolean activate) {
        super(TYPE.addByte(Property.boolToByte(activate)), true);
    }

    ActivateInactivateParkingBrake(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
