package com.highmobility.autoapi.vehiclestatus;
import com.highmobility.autoapi.Command.Identifier;
import com.highmobility.autoapi.CommandParseException;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class Windows extends FeatureState {
    public Windows(byte[] bytes) throws CommandParseException {
        super(Identifier.WINDOWS);

        if (bytes.length < 5) throw new CommandParseException();

    }
}