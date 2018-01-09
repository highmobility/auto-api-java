package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * Manually start or stop defrosting. The result is sent through the evented Climate State message.
 */
public class StartStopDefrosting extends Command {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x05);

    public StartStopDefrosting(boolean start) {
        super(TYPE.addByte(Property.boolToByte(start)), true);
    }


    StartStopDefrosting(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
