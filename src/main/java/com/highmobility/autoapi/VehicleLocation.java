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
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.Property;

/**
 * This command is sent when a Get Vehicle Location message is received by the car.
 */
public class VehicleLocation extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.VEHICLE_LOCATION, 0x01);

    private static final byte COORDINATES_IDENTIFIER = 0x01;
    private static final byte HEADING_IDENTIFIER = 0x02;
    private static final byte ALTITUDE_IDENTIFIER = 0x03;

    private CoordinatesProperty coordinates;
    private Float heading;

    private Float altitude;

    /**
     * @return The vehicle coordinates.
     */
    public CoordinatesProperty getCoordinates() {
        return coordinates;
    }

    /**
     * @return The heading.
     */
    public Float getHeading() {
        return heading;
    }

    /**
     * @return The altitude in meters above the WGS 84 reference ellipsoid.
     */
    public Float getAltitude() {
        return altitude;
    }

    public VehicleLocation(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case COORDINATES_IDENTIFIER:
                    coordinates = new CoordinatesProperty(property.getPropertyBytes());
                    break;
                case HEADING_IDENTIFIER:
                    heading = Property.getFloat(property.getValueBytes());
                    break;
                case ALTITUDE_IDENTIFIER:
                    altitude = Property.getFloat(property.getValueBytes());
                    break;
            }
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
        private Float heading;
        private CoordinatesProperty coordinates;

        private Float altitude;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param heading The heading.
         * @return The builder.
         */
        public Builder setHeading(float heading) {
            this.heading = heading;
            addProperty(new FloatProperty(HEADING_IDENTIFIER, heading));
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
        public Builder setAltitude(float altitude) {
            this.altitude = altitude;
            addProperty(new FloatProperty(ALTITUDE_IDENTIFIER, altitude));
            return this;
        }

        public VehicleLocation build() {
            return new VehicleLocation(this);
        }
    }
}