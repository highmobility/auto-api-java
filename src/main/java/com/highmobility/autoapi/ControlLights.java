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

import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.ByteProperty;
import com.highmobility.autoapi.property.ColorProperty;
import com.highmobility.autoapi.property.FrontExteriorLightState;
import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Set the lights state. The result is sent through the Lights State message.
 */
public class ControlLights extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.LIGHTS, 0x02);

    /**
     * Set the lights state. The result is sent through the Lights State message.
     *
     * @param frontExteriorLightState Front exterior light state
     * @param rearExteriorLightActive Rear exterior light state
     * @param interiorLightActive Interior light state
     * @param ambientColor Ambient color with rgba values from 0-255
     *
     * @throws IllegalArgumentException If all arguments are null
     */
    public ControlLights(FrontExteriorLightState frontExteriorLightState,
                                       Boolean rearExteriorLightActive,
                                       Boolean interiorLightActive,
                                       int[] ambientColor) {
        super(TYPE, getProperties(frontExteriorLightState, rearExteriorLightActive, interiorLightActive, ambientColor));
    }

    static HMProperty[] getProperties(FrontExteriorLightState frontExteriorLightState,
                                      Boolean rearExteriorLightActive,
                                      Boolean interiorLightActive,
                                      int[] ambientColor) {
        List<Property> properties = new ArrayList<>();

        if (frontExteriorLightState != null) {
            ByteProperty prop = new ByteProperty((byte) 0x01, frontExteriorLightState.getByte());
            properties.add(prop);
        }

        if (rearExteriorLightActive != null) {
            BooleanProperty prop = new BooleanProperty((byte) 0x02, rearExteriorLightActive);
            properties.add(prop);
        }

        if (interiorLightActive != null) {
            BooleanProperty prop = new BooleanProperty((byte) 0x03, interiorLightActive);
            properties.add(prop);
        }

        if (ambientColor != null) {
            ColorProperty prop = new ColorProperty((byte) 0x04, ambientColor);
            properties.add(prop);
        }

        return properties.toArray(new Property[properties.size()]);
    }

    ControlLights(byte[] bytes) {
        super(bytes);
    }
}
