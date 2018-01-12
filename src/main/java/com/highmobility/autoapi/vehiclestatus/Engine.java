package com.highmobility.autoapi.vehiclestatus;
import com.highmobility.autoapi.Command.Identifier;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Property;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class Engine extends FeatureState {
    boolean on;

    /**
     *
     * @return the ignition state
     */
    public boolean isOn() {
        return on;
    }

    public Engine(boolean on) {
        super(Identifier.ENGINE);
        this.on = on;

        bytes = getBytesWithOneByteLongFields(1);
        bytes[3] = Property.boolToByte(on);
    }

    public Engine(byte[] bytes) throws CommandParseException {
        super(Identifier.ENGINE);

        if (bytes.length < 4) throw new CommandParseException();
        on = Property.getBool(bytes[3]);

        this.bytes = bytes;
    }
}