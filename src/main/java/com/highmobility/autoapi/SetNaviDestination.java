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

import com.highmobility.autoapi.value.Coordinates;
import com.highmobility.autoapi.property.Property;


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

    private Property<Coordinates> coordinates = new Property<>(Coordinates.class,
            COORDINATES_IDENTIFIER);
    private Property<String> name = new Property(String.class, NAME_IDENTIFIER);

    /**
     * @return The destination coordinates.
     */
    public Property<Coordinates> getCoordinates() {
        return coordinates;
    }

    /**
     * @return The destination name.
     */
    @Nullable public Property<String> getName() {
        return name;
    }

    /**
     * @param coordinates The destination coordinates.
     * @param name        The destination name.
     */
    public SetNaviDestination(Coordinates coordinates, @Nullable String name) {
        super(TYPE);

        List<Property> properties = new ArrayList<>();

        this.coordinates.update(coordinates);
        properties.add(this.coordinates);

        if (name != null) {
            this.name.update(name);
            properties.add(this.name);
        }

        createBytes(properties);
    }

    SetNaviDestination(byte[] bytes) {
        super(bytes);

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case COORDINATES_IDENTIFIER:
                        return coordinates.update(p);
                    case NAME_IDENTIFIER:
                        return name.update(p);

                }

                return null;
            });
        }
    }
}
