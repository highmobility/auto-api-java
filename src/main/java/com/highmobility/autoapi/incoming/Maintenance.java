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

package com.highmobility.autoapi.incoming;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Property;

import java.util.Arrays;

/**
 * Created by root on 6/28/17.
 */

public class Maintenance extends IncomingCommand {
    private int kilometersToNextService;
    private int daysToNextService;

    /**
     *
     * @return Amount of kilometers until next servicing of the car
     */
    public int getKilometersToNextService() {
        return kilometersToNextService;
    }

    /**
     *
     * @return Number of days until next servicing of the car, whereas negative is overdue
     */
    public int getDaysToNextService() {
        return daysToNextService;
    }

    public Maintenance(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length != 8) throw new CommandParseException();
        daysToNextService = Property.getUnsignedInt(Arrays.copyOfRange(bytes, 3, 3 + 2));
        kilometersToNextService = Property.getUnsignedInt(Arrays.copyOfRange(bytes, 5, 5 + 3));
    }
}
