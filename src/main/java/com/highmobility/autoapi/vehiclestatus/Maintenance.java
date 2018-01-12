package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Property;
import com.highmobility.utils.Bytes;

import java.util.Arrays;

/**
 * Created by root on 6/28/17.
 */

public class Maintenance extends FeatureState {
    private int daysToNextService;
    private int kilometersToNextService;

    /**
     *
     * @return Number of days until next servicing of the car, whereas negative is overdue
     */
    public int getDaysToNextService() {
        return daysToNextService;
    }

    /**
     *
     * @return Amount of kilometers until next servicing of the car
     */
    public int getKilometersToNextService() {
        return kilometersToNextService;
    }

    public Maintenance(int daysToNextService, int kilometersToNextService) {
        super(Command.Identifier.MAINTENANCE);
        this.kilometersToNextService = kilometersToNextService;
        this.daysToNextService = daysToNextService;

        bytes = getBytesWithMoreThanOneByteLongFields(2, 3);
        byte[] daysToNextServiceBytes = Property.intToBytes(daysToNextService, 2);
        byte[] kilometersToNextServiceBytes = Property.intToBytes(kilometersToNextService, 3);

        Bytes.setBytes(bytes, daysToNextServiceBytes, 3);
        Bytes.setBytes(bytes, kilometersToNextServiceBytes, 5);
    }

    public Maintenance(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.MAINTENANCE);

        if (bytes.length != 8) throw new CommandParseException();
        daysToNextService = Property.getUnsignedInt(Arrays.copyOfRange(bytes, 3, 3 + 2));
        kilometersToNextService = Property.getUnsignedInt(Arrays.copyOfRange(bytes, 5, 5 + 3));

        this.bytes = bytes;
    }
}
