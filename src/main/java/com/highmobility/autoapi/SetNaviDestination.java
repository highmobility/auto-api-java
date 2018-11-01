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
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Set the navigation destination. This will be forwarded to the navigation system of the car.
 */
public class SetNaviDestination extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.NAVI_DESTINATION, 0x02);

    private static final byte COORDINATES_IDENTIFIER = 0x07;
    private static final byte NAME_IDENTIFIER = 0x02;

    private CoordinatesProperty coordinates;
    private String name;

    /**
     * @return The destination coordinates.
     */
    public CoordinatesProperty getCoordinates() {
        return coordinates;
    }

    /**
     * @return The destination name.
     */
    @Nullable public String getName() {
        return name;
    }

    /**
     * @param coordinates The destination coordinates.
     * @param name        The destination name.
     * @throws IllegalArgumentException if all arguments are null
     */
    public SetNaviDestination(CoordinatesProperty coordinates, @Nullable String name) {
        super(TYPE, getProperties(coordinates, name));
        this.coordinates = coordinates;
        this.name = name;
    }

    static HMProperty[] getProperties(CoordinatesProperty coordinates, String name) {
        List<Property> properties = new ArrayList<>();

        if (coordinates != null) {
            coordinates.setIdentifier(COORDINATES_IDENTIFIER);
            properties.add(coordinates);
        }

        if (name != null) {
            Property prop = new StringProperty(NAME_IDENTIFIER, name);
            properties.add(prop);
        }

        return properties.toArray(new Property[properties.size()]);
    }

    SetNaviDestination(byte[] bytes) {
        super(bytes);
        for (Property property : properties) {
            switch (property.getPropertyIdentifier()) {
                case COORDINATES_IDENTIFIER:
                    coordinates = new CoordinatesProperty(property.getPropertyBytes());
                    break;
                case NAME_IDENTIFIER:
                    name = Property.getString(property.getValueBytes());
                    break;
            }
        }
    }
}
