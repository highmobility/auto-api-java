package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Command;

/**
 * Created by ttiganik on 21/12/2016.
 */

public class RooftopState extends FeatureState {
    /**
     * The possible states of the rooftop.
     */
    float dimmingPercentage;
    float openPercentage;

    public RooftopState(float dimmingPercentage, float openPercentage) {
        super(Command.Identifier.ROOFTOP);
        this.dimmingPercentage = dimmingPercentage;
        this.openPercentage = openPercentage;

        bytes = getBytesWithOneByteLongFields(2);
        bytes[3] = (byte)(int)(dimmingPercentage * 100);
        bytes[4] = (byte)(int)(openPercentage * 100);
    }

    RooftopState(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.ROOFTOP);

        if (bytes.length != 5) throw new CommandParseException();

        dimmingPercentage =  (int)bytes[3] / 100f;
        openPercentage =  (int)bytes[4] / 100f;
        this.bytes = bytes;
    }

    /**
     *
     * @return the dim percentage of the rooftop
     */
    public float getDimmingPercentage() {
        return dimmingPercentage;
    }

    /**
     *
     * @return the percentage of how much the rooftop is open
     */
    public float getOpenPercentage() {
        return openPercentage;
    }
}
