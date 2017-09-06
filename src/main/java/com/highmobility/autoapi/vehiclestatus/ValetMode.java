package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Command;

/**
 * Created by ttiganik on 16/12/2016.
 */

public class ValetMode extends FeatureState {
    boolean isActive;

    ValetMode(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.VALET_MODE);
        if (bytes.length != 4) throw new CommandParseException();

        isActive = bytes[3] == 0x01;
    }

    /**
     *
     * @return Whether Valet Mode is active
     */
    public boolean isActive() {
        return isActive;
    }
}
