package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Axle;
import com.highmobility.utils.Bytes;

/**
 * Set the spring rate. The result is sent through the Chassis Settings message.
 */
public class SetSpringRate extends Command {
    public static final Type TYPE = new Type(Identifier.CHASSIS_SETTINGS, 0x04);

    public SetSpringRate(Axle axle, int springRate) {
        super(getValues(axle, springRate), true);
    }

    static byte[] getValues(Axle axle, int springRate) {
        byte[] bytes = TYPE.getIdentifierAndType();
        return Bytes.concatBytes(bytes, new byte[] { axle.getByte(), (byte)springRate });
    }

    SetSpringRate(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
