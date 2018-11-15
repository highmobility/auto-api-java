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
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Navi Destination command is received by the car.
 */
public class NaviDestination extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.NAVI_DESTINATION, 0x01);

    private static final byte COORDINATES_IDENTIFIER = 0x07;
    private static final byte NAME_IDENTIFIER = 0x02;

    private CoordinatesProperty coordinates;
    private String name;

    /**
     * @return The coordinates.
     */
    @Nullable public CoordinatesProperty getCoordinates() {
        return coordinates;
    }

    /**
     * @return The name.
     */
    @Nullable public String getName() {
        return name;
    }

    public NaviDestination(byte[] bytes) {
        super(bytes);

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case COORDINATES_IDENTIFIER:
                        coordinates = new CoordinatesProperty(p.getPropertyBytes());
                        return coordinates;
                    case NAME_IDENTIFIER:
                        name = Property.getString(p.getValueBytes());
                        return name;
                }
                
                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private NaviDestination(Builder builder) {
        super(builder);
        name = builder.name;
        coordinates = builder.coordinates;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private String name;
        private CoordinatesProperty coordinates;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param coordinates The coordinates.
         * @return The builder.
         */
        public Builder setCoordinates(CoordinatesProperty coordinates) {
            this.coordinates = coordinates;
            coordinates.setIdentifier(COORDINATES_IDENTIFIER);
            addProperty(coordinates);
            return this;
        }

        /**
         * @param name The name.
         * @return The builder.
         */
        public Builder setName(String name) {
            this.name = name;
            addProperty(new StringProperty(NAME_IDENTIFIER, name));
            return this;
        }

        public NaviDestination build() {
            return new NaviDestination(this);
        }
    }
}