package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * Attempt to turn the car engine on or off. The result is sent through the evented Ignition State
 * message with either 0x00 Engine Off or 0x01 Engine On.
 */
public class TurnEngineOnOff extends Command {
    public static final Type TYPE = new Type(Identifier.ENGINE, 0x02);

    public TurnEngineOnOff(boolean on)  {
        super(TYPE.addByte(Property.boolToByte(on)), true);
    }

    TurnEngineOnOff(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
