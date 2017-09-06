package com.highmobility.autoapi.vehiclestatus;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class Fueling extends FeatureState {
    public Fueling(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.FUELING);

        if (bytes.length < 5) throw new CommandParseException();

    }
}