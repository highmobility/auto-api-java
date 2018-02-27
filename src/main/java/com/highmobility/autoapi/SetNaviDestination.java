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

import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Set the navigation destination. This will be forwarded to the navigation system of the car.
 */
public class SetNaviDestination extends CommandWithExistingProperties {
    public static final Type TYPE = new Type(Identifier.NAVI_DESTINATION, 0x02);

    /**
     * @param latitude  the latitude of the destination
     * @param longitude the longitude of the destination
     * @param name      the destination name
     * @throws IllegalArgumentException if all arguments are null
     */
    public SetNaviDestination(Float latitude, Float longitude, String name) {
        super(TYPE, getProperties(latitude, longitude, name));
    }

    static HMProperty[] getProperties(Float latitude, Float longitude, String name) {
        List<Property> properties = new ArrayList<>();

        if (latitude != null) {
            Property prop = new FloatProperty((byte) 0x01, latitude);
            properties.add(prop);
        }

        if (longitude != null) {
            Property prop = new FloatProperty((byte) 0x02, longitude);
            properties.add(prop);
        }

        if (name != null) {
            Property prop = new StringProperty((byte) 0x03, name);
            properties.add(prop);
        }

        return properties.toArray(new Property[properties.size()]);
    }

    SetNaviDestination(byte[] bytes) {
        super(bytes);
    }
}
