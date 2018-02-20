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

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

import java.util.Calendar;

/**
 * This message is sent when a Get Vehicle Time message is received by the car. The local time of
 * the car is returned, hence the UTC timezone offset is included as well.
 */
public class VehicleTime extends CommandWithExistingProperties {
    public static final Type TYPE = new Type(Identifier.VEHICLE_TIME, 0x01);

    Calendar vehicleTime;

    /**
     *
     * @return Vehicle time
     */
    public Calendar getVehicleTime() {
        return vehicleTime;
    }

    public VehicleTime(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    vehicleTime = Property.getCalendar(property.getValueBytes());
                    break;
            }
        }
    }
}