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

/**
 * Set the navigation destination. This will be forwarded to the navigation system of the car.
 */
public class SetNaviDestination extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.NAVI_DESTINATION, 0x02);

    /**
     * @param coordinates   the destination coordinates. Property identifier is not needed.
     * @param name          the destination name
     * @throws IllegalArgumentException if all arguments are null
     */
    public SetNaviDestination(CoordinatesProperty coordinates, String name) {
        super(TYPE, getProperties(coordinates, name));
    }

    static HMProperty[] getProperties(CoordinatesProperty coordinates, String name) {
        List<Property> properties = new ArrayList<>();

        if (coordinates != null) {
            coordinates.setIdentifier((byte) 0x01);
            properties.add(coordinates);
        }

        if (name != null) {
            Property prop = new StringProperty((byte) 0x02, name);
            properties.add(prop);
        }

        return properties.toArray(new Property[properties.size()]);
    }

    SetNaviDestination(byte[] bytes) {
        super(bytes);
    }
}
