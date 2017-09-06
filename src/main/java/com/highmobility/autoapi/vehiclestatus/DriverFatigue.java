package com.highmobility.autoapi.vehiclestatus;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class DriverFatigue extends FeatureState {
    public DriverFatigue(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.DRIVER_FATIGUE);

        if (bytes.length < 5) throw new CommandParseException();

    }
}