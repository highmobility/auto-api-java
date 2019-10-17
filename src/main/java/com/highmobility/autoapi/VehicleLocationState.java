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
import com.highmobility.autoapi.value.Coordinates;

/**
 * The vehicle location state
 */
public class VehicleLocationState extends SetCommand {
    public static final int IDENTIFIER = Identifier.VEHICLE_LOCATION;

    public static final byte IDENTIFIER_COORDINATES = 0x04;
    public static final byte IDENTIFIER_HEADING = 0x05;
    public static final byte IDENTIFIER_ALTITUDE = 0x06;

    Property<Coordinates> coordinates = new Property(Coordinates.class, IDENTIFIER_COORDINATES);
    Property<Double> heading = new Property(Double.class, IDENTIFIER_HEADING);
    Property<Double> altitude = new Property(Double.class, IDENTIFIER_ALTITUDE);

    /**
     * @return The coordinates
     */
    public Property<Coordinates> getCoordinates() {
        return coordinates;
    }

    /**
     * @return Heading in degrees
     */
    public Property<Double> getHeading() {
        return heading;
    }

    /**
     * @return Altitude in meters above the WGS 84 reference ellipsoid
     */
    public Property<Double> getAltitude() {
        return altitude;
    }

    VehicleLocationState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_COORDINATES: return coordinates.update(p);
                    case IDENTIFIER_HEADING: return heading.update(p);
                    case IDENTIFIER_ALTITUDE: return altitude.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private VehicleLocationState(Builder builder) {
        super(builder);

        coordinates = builder.coordinates;
        heading = builder.heading;
        altitude = builder.altitude;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<Coordinates> coordinates;
        private Property<Double> heading;
        private Property<Double> altitude;

        public Builder() {
            super(IDENTIFIER);
        }

        public VehicleLocationState build() {
            return new VehicleLocationState(this);
        }

        /**
         * @param coordinates The coordinates
         * @return The builder
         */
        public Builder setCoordinates(Property<Coordinates> coordinates) {
            this.coordinates = coordinates.setIdentifier(IDENTIFIER_COORDINATES);
            addProperty(this.coordinates);
            return this;
        }
        
        /**
         * @param heading Heading in degrees
         * @return The builder
         */
        public Builder setHeading(Property<Double> heading) {
            this.heading = heading.setIdentifier(IDENTIFIER_HEADING);
            addProperty(this.heading);
            return this;
        }
        
        /**
         * @param altitude Altitude in meters above the WGS 84 reference ellipsoid
         * @return The builder
         */
        public Builder setAltitude(Property<Double> altitude) {
            this.altitude = altitude.setIdentifier(IDENTIFIER_ALTITUDE);
            addProperty(this.altitude);
            return this;
        }
    }
}