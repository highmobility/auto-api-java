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

import javax.annotation.Nullable;

/**
 * This command is sent when a Get Vehicle Time message is received by the car. The local time of
 * the car is returned, hence the UTC timezone offset is included as well.
 */
public class VehicleTime extends Command {
    public static final Type TYPE = new Type(Identifier.VEHICLE_TIME, 0x01);

    private static final byte IDENTIFIER = 0x01;

    Property<Calendar> vehicleTime = new Property(Calendar.class, IDENTIFIER);

    /**
     * @return The vehicle time.
     */
    @Nullable public Property<Calendar> getVehicleTime() {
        return vehicleTime;
    }

    VehicleTime(byte[] bytes) {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                if (p.getPropertyIdentifier() == IDENTIFIER) {
                    return vehicleTime.update(p);
                }
                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private VehicleTime(Builder builder) {
        super(builder);
        vehicleTime = builder.vehicleTime;
    }

    public static final class Builder extends Command.Builder {
        Property<Calendar> vehicleTime;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param vehicleTime The vehicle time.
         * @return The builder.
         */
        public Builder setVehicleTime(Property<Calendar> vehicleTime) {
            this.vehicleTime = vehicleTime;
            vehicleTime.setIdentifier(IDENTIFIER);
            addProperty(vehicleTime);
            return this;
        }

        public VehicleTime build() {
            return new VehicleTime(this);
        }
    }
}