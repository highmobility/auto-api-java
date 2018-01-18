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
