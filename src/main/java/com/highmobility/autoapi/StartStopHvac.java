package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * Start or stop the HVAC system to reach driver and passenger set temperatures. The car will use
 * cooling, defrosting and defogging as appropriate. The result is sent through the evented Climate
 * State message.
 */
public class StartStopHvac extends Command {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x03);

    public StartStopHvac(boolean start) {
        super(TYPE.addByte(Property.boolToByte(start)), true);
    }

    StartStopHvac(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
