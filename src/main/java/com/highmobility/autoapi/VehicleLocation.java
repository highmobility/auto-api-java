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

import com.highmobility.autoapi.property.CoordinatesProperty;
import com.highmobility.autoapi.property.DoubleProperty;

import javax.annotation.Nullable;

/**
 * This command is sent when a Get Vehicle Location message is received by the car.
 */
public class VehicleLocation extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.VEHICLE_LOCATION, 0x01);

    private static final byte COORDINATES_IDENTIFIER = 0x04;
    private static final byte HEADING_IDENTIFIER = 0x05;
    private static final byte ALTITUDE_IDENTIFIER = 0x06;

    private CoordinatesProperty coordinates;
    private DoubleProperty heading;
    private DoubleProperty altitude;

    /**
     * @return The vehicle coordinates.
     */
    @Nullable public CoordinatesProperty getCoordinates() {
        return coordinates;
    }

    /**
     * @return The heading.
     */
    @Nullable public DoubleProperty getHeading() {
        return heading;
    }

    /**
     * @return The altitude in meters above the WGS 84 reference ellipsoid.
     */
    @Nullable public DoubleProperty getAltitude() {
        return altitude;
    }

    VehicleLocation(byte[] bytes) {
        super(bytes);

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case COORDINATES_IDENTIFIER:
                        coordinates = new CoordinatesProperty(p.getByteArray());
                        return coordinates;
                    case HEADING_IDENTIFIER:
                        heading = new DoubleProperty(p);
                        return heading;
                    case ALTITUDE_IDENTIFIER:
                        altitude = new DoubleProperty(p);
                        return altitude;
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private VehicleLocation(Builder builder) {
        super(builder);
        heading = builder.heading;
        coordinates = builder.coordinates;
        altitude = builder.altitude;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private CoordinatesProperty coordinates;
        private DoubleProperty heading;
        private DoubleProperty altitude;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param heading The heading.
         * @return The builder.
         */
        public Builder setHeading(DoubleProperty heading) {
            this.heading = heading;
            heading.setIdentifier(HEADING_IDENTIFIER);
            addProperty(heading);
            return this;
        }

        /**
         * @param coordinates The vehicle coordinates.
         * @return The builder.
         */
        public Builder setCoordinates(CoordinatesProperty coordinates) {
            this.coordinates = coordinates;
            coordinates.setIdentifier(COORDINATES_IDENTIFIER);
            addProperty(coordinates);
            return this;
        }

        /**
         * @param altitude The altitude in meters above the WGS 84 reference ellipsoid
         * @return The builder.
         */
        public Builder setAltitude(DoubleProperty altitude) {
            this.altitude = altitude;
            altitude.setIdentifier(ALTITUDE_IDENTIFIER);
            addProperty(altitude);
            return this;
        }

        public VehicleLocation build() {
            return new VehicleLocation(this);
        }
    }
}