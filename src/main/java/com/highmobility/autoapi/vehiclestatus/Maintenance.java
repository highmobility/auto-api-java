/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

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
