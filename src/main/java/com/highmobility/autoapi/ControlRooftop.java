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

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Set the rooftop state. The result is sent through the evented Rooftop State command.
 */
public class ControlRooftop extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.ROOFTOP, 0x02);

    private Float dimmingPercentage;
    private Float openPercentage;

    /**
     * @return The dimming percentage.
     */
    public Float getDimmingPercentage() {
        return dimmingPercentage;
    }

    /**
     * @return The open percentage.
     */
    public Float getOpenPercentage() {
        return openPercentage;
    }

    /**
     * @param dimmingPercentage The dimming percentage.
     * @param openPercentage    The rooftop open percentage.
     */
    public ControlRooftop(Float dimmingPercentage, Float openPercentage) {
        super(TYPE, getProperties(dimmingPercentage, openPercentage));
        this.dimmingPercentage = dimmingPercentage;
        this.openPercentage = openPercentage;
    }

    static HMProperty[] getProperties(Float dimmingPercentage, Float openPercentage) {
        List<Property> properties = new ArrayList<>();

        if (dimmingPercentage != null) {
            IntegerProperty prop = new IntegerProperty(RooftopState.DIMMING_IDENTIFIER, Property
                    .floatToIntPercentage(dimmingPercentage), 1);
            properties.add(prop);
        }

        if (openPercentage != null) {
            IntegerProperty prop = new IntegerProperty(RooftopState.OPEN_IDENTIFIER, Property
                    .floatToIntPercentage(openPercentage), 1);
            properties.add(prop);
        }

        return properties.toArray(new Property[properties.size()]);
    }

    ControlRooftop(byte[] bytes) {
        super(bytes);

        Enumeration e = getPropertiesEnumeration();
        while (e.hasMoreElements()) {
            Property prop = (Property) e.nextElement();
            switch (prop.getPropertyIdentifier()) {
                case RooftopState.DIMMING_IDENTIFIER:
                    dimmingPercentage = Property.getUnsignedInt(prop.getValueByte()) / 100f;
                    break;
                case RooftopState.OPEN_IDENTIFIER:
                    openPercentage = Property.getUnsignedInt(prop.getValueByte()) / 100f;
                    break;
            }
        }
    }
}
