package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * Activate or deacticate valet mode. The result is sent through the evented Valet Mode message with
 * either the mode 0x00 Deactivated or 0x01 Activated.
 */
public class ActivateDeactivateValetMode extends Command {
    public static final Type TYPE = new Type(Identifier.VALET_MODE, 0x02);

    /**
     *
     * @param activate A boolean indicating whether to activate the valet mode.
     * @return The command bytes
     */
    public ActivateDeactivateValetMode(boolean activate) {
        super(TYPE.addByte(Property.boolToByte(activate)), true);
    }

    ActivateDeactivateValetMode(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
