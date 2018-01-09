package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * Manually start or stop ionising. The result is sent through the evented Climate State message.
 */
public class StartStopIonizing extends Command {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x06);

    public StartStopIonizing(boolean start) {
        super(TYPE.addByte(Property.boolToByte(start)), true);
    }

    StartStopIonizing(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
