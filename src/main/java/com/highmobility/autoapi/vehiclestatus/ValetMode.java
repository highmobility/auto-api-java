package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.Property;
import com.highmobility.utils.Bytes;

/**
 * Created by ttiganik on 16/12/2016.
 */

public class ValetMode extends FeatureState {
    boolean isActive;

    /**
     *
     * @return Whether Valet Mode is active
     */
    public boolean isActive() {
        return isActive;
    }

    public ValetMode(boolean isActive) {
        super(Command.Identifier.VALET_MODE);
        this.isActive = isActive;
        bytes = getBytesWithOneByteLongFields(1);
        bytes[3] = Property.boolToByte(isActive);
    }

    ValetMode(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.VALET_MODE);
        if (bytes.length != 4) throw new CommandParseException();
        isActive = bytes[3] == 0x01;
        this.bytes = bytes;
    }
}
