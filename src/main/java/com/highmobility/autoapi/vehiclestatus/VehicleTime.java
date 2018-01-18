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
import com.highmobility.autoapi.Command.Identifier;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.Property;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class VehicleTime extends FeatureState {
    Calendar time;

    /**
     *
     * @return Vehicle time
     */
    public Calendar getVehicleTime() {
        return time;
    }

    public VehicleTime(byte[] bytes) throws CommandParseException {
        super(Identifier.VEHICLE_TIME);
        if (bytes.length < 4) throw new CommandParseException();
        time = Property.getCalendar(Arrays.copyOfRange(bytes, 3, 11));
    }
}