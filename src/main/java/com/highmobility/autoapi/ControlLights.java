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
 * Set the lights state. The result is sent through the Lights State command.
 */
public class ControlLights extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.LIGHTS, 0x12);

    private static final byte FRONT_EXTERIOR_LIGHT_STATE_IDENTIFIER = 0x01;
    private static final byte REAR_EXTERIOR_LIGHT_ACTIVE_IDENTIFIER = 0x02;
    private static final byte INTERIOR_LIGHT_ACTIVE_IDENTIFIER = 0x03;
    private static final byte AMBIENT_COLOR_IDENTIFIER = 0x04;

    private FrontExteriorLightState frontExteriorLightState;
    private Boolean rearExteriorLightActive;
    private Boolean interiorLightActive;
    private int[] ambientColor;

    /**
     * @return The front exterior light state.
     */
    public FrontExteriorLightState getFrontExteriorLightState() {
        return frontExteriorLightState;
    }

    /**
     * @return Rear exterior light state.
     */
    public Boolean getRearExteriorLightActive() {
        return rearExteriorLightActive;
    }

    /**
     * @return Interior light state.
     */
    public Boolean getInteriorLightActive() {
        return interiorLightActive;
    }

    /**
     * @return Ambient color, in rgba values from 0-255.
     */
    public int[] getAmbientColor() {
        return ambientColor;
    }

    /**
     * Set the lights state. The result is sent through the Lights State command.
     *
     * @param frontExteriorLightState Front exterior light state.
     * @param rearExteriorLightActive Rear exterior light state.
     * @param interiorLightActive     Interior light state.
     * @param ambientColor            Ambient color with rgba values from 0-255.
     */
    public ControlLights(FrontExteriorLightState frontExteriorLightState,
                         Boolean rearExteriorLightActive,
                         Boolean interiorLightActive,
                         int[] ambientColor) {
        super(TYPE, getProperties(frontExteriorLightState, rearExteriorLightActive,
                interiorLightActive, ambientColor));
        this.frontExteriorLightState = frontExteriorLightState;
        this.rearExteriorLightActive = rearExteriorLightActive;
        this.interiorLightActive = interiorLightActive;
        this.ambientColor = ambientColor;
    }

    static Property[] getProperties(FrontExteriorLightState frontExteriorLightState,
                                      Boolean rearExteriorLightActive,
                                      Boolean interiorLightActive,
                                      int[] ambientColor) {
        List<Property> properties = new ArrayList<>();

        if (frontExteriorLightState != null) {
            ByteProperty prop = new ByteProperty(FRONT_EXTERIOR_LIGHT_STATE_IDENTIFIER,
                    frontExteriorLightState.getByte());
            properties.add(prop);
        }

        if (rearExteriorLightActive != null) {
            BooleanProperty prop = new BooleanProperty(REAR_EXTERIOR_LIGHT_ACTIVE_IDENTIFIER,
                    rearExteriorLightActive);
            properties.add(prop);
        }

        if (interiorLightActive != null) {
            BooleanProperty prop = new BooleanProperty(INTERIOR_LIGHT_ACTIVE_IDENTIFIER,
                    interiorLightActive);
            properties.add(prop);
        }

        if (ambientColor != null) {
            ColorProperty prop = new ColorProperty(AMBIENT_COLOR_IDENTIFIER, ambientColor);
            properties.add(prop);
        }

        return properties.toArray(new Property[0]);
    }

    ControlLights(byte[] bytes) throws CommandParseException {
        super(bytes);

        Property[] properties = getProperties();

        for (int i = 0; i < properties.length; i++) {
            Property prop = properties[i];

            switch (prop.getPropertyIdentifier()) {
                case FRONT_EXTERIOR_LIGHT_STATE_IDENTIFIER:
                    frontExteriorLightState = FrontExteriorLightState.fromByte(prop.getValueByte());
                    break;
                case REAR_EXTERIOR_LIGHT_ACTIVE_IDENTIFIER:
                    rearExteriorLightActive = Property.getBool(prop.getValueByte());
                    break;
                case INTERIOR_LIGHT_ACTIVE_IDENTIFIER:
                    interiorLightActive = Property.getBool(prop.getValueByte());
                    break;
                case AMBIENT_COLOR_IDENTIFIER:
                    ambientColor = new ColorProperty(prop.getPropertyBytes()).getAmbientColor();
                    break;
            }
        }
    }
}
