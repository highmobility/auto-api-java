package com.highmobility.autoapi;

import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.ByteProperty;
import com.highmobility.autoapi.property.ColorProperty;
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
     * @return the command bytes
     * @throws IllegalArgumentException If all arguments are null
     */
    public ControlLights(LightsState.FrontExteriorLightState frontExteriorLightState,
                                       Boolean rearExteriorLightActive,
                                       Boolean interiorLightActive,
                                       int[] ambientColor) {
        super(TYPE, getProperties(frontExteriorLightState, rearExteriorLightActive, interiorLightActive, ambientColor));
    }

    static HMProperty[] getProperties(LightsState.FrontExteriorLightState frontExteriorLightState,
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

    ControlLights(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
