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

/**
 * This command is sent when a Get Light Conditions is received by the car.
 */
public class LightConditions extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.LIGHT_CONDITIONS, 0x01);

    Float outsideLight;
    Float insideLight;

    /**
     * @return Measured outside illuminance in lux
     */
    public Float getOutsideLight() {
        return outsideLight;
    }

    /**
     * @return Measured inside illuminance in lux
     */
    public Float getInsideLight() {
        return insideLight;
    }

    public LightConditions(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    outsideLight = Property.getFloat(property.getValueBytes());
                    break;
                case 0x02:
                    insideLight = Property.getFloat(property.getValueBytes());
                    break;
            }
        }
    }

    @Override public boolean isState() {
        return true;
    }
}