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

import com.highmobility.autoapi.property.Coordinates;
import com.highmobility.autoapi.property.ObjectProperty;

import javax.annotation.Nullable;

/**
 * This command is sent when a Get Vehicle Location message is received by the car.
 */
public class VehicleLocation extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.VEHICLE_LOCATION, 0x01);

    private static final byte IDENTIFIER_COORDINATES = 0x04;
    private static final byte IDENTIFIER_HEADING = 0x05;
    private static final byte IDENTIFIER_ALTITUDE = 0x06;

    private ObjectProperty<Coordinates> coordinates = new ObjectProperty<>(Coordinates.class, IDENTIFIER_COORDINATES);
    private ObjectProperty<Double> heading = new ObjectProperty<>(Double.class, IDENTIFIER_HEADING);
    private ObjectProperty<Double> altitude = new ObjectProperty<>(Double.class, IDENTIFIER_ALTITUDE);

    /**
     * @return The vehicle coordinates.
     */
    @Nullable public ObjectProperty<Coordinates> getCoordinates() {
        return coordinates;
    }

    /**
     * @return The heading.
     */
    @Nullable public ObjectProperty<Double> getHeading() {
        return heading;
    }

    /**
     * @return The altitude in meters above the WGS 84 reference ellipsoid.
     */
    @Nullable public ObjectProperty<Double> getAltitude() {
        return altitude;
    }

    VehicleLocation(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_COORDINATES:
                        return coordinates.update(p);
                    case IDENTIFIER_HEADING:
                        return heading.update(p);
                    case IDENTIFIER_ALTITUDE:
                        return altitude.update(p);
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
        private ObjectProperty<Coordinates> coordinates;
        private ObjectProperty<Double> heading;
        private ObjectProperty<Double> altitude;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param heading The heading.
         * @return The builder.
         */
        public Builder setHeading(ObjectProperty<Double> heading) {
            this.heading = heading;
            heading.setIdentifier(IDENTIFIER_HEADING);
            addProperty(heading);
            return this;
        }

        /**
         * @param coordinates The vehicle coordinates.
         * @return The builder.
         */
        public Builder setCoordinates(ObjectProperty<Coordinates> coordinates) {
            this.coordinates = coordinates;
            coordinates.setIdentifier(IDENTIFIER_COORDINATES);
            addProperty(coordinates);
            return this;
        }

        /**
         * @param altitude The altitude in meters above the WGS 84 reference ellipsoid
         * @return The builder.
         */
        public Builder setAltitude(ObjectProperty<Double> altitude) {
            this.altitude = altitude;
            altitude.setIdentifier(IDENTIFIER_ALTITUDE);
            addProperty(altitude);
            return this;
        }

        public VehicleLocation build() {
            return new VehicleLocation(this);
        }
    }
}