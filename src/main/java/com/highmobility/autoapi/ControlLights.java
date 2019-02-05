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

import com.highmobility.autoapi.property.IntegerArrayProperty;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.FogLight;
import com.highmobility.autoapi.property.lights.FrontExteriorLightState;
import com.highmobility.autoapi.property.lights.InteriorLamp;
import com.highmobility.autoapi.property.lights.LightLocation;
import com.highmobility.autoapi.property.lights.ReadingLamp;
import com.highmobility.autoapi.property.value.Location;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Set the lights state. The result is sent through the Lights State command.
 */
public class ControlLights extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.LIGHTS, 0x12);

    private static final byte IDENTIFIER_FRONT_EXTERIOR_LIGHT_STATE = 0x01;
    private static final byte IDENTIFIER_REAR_EXTERIOR_LIGHT_ACTIVE = 0x02;
    private static final byte IDENTIFIER_AMBIENT_COLOR = 0x04;

    private static final byte IDENTIFIER_FOG_LIGHT = 0x07;
    private static final byte IDENTIFIER_READING_LAMP = 0x08;
    private static final byte IDENTIFIER_INTERIOR_LAMP = 0x09;

    private FrontExteriorLightState frontExteriorLightState =
            new FrontExteriorLightState(IDENTIFIER_FRONT_EXTERIOR_LIGHT_STATE);
    private ObjectProperty<Boolean> rearExteriorLightActive =
            new ObjectProperty<>(Boolean.class, IDENTIFIER_REAR_EXTERIOR_LIGHT_ACTIVE);
    private IntegerArrayProperty ambientColor = new IntegerArrayProperty(IDENTIFIER_AMBIENT_COLOR);

    private FogLight[] fogLights;
    private ReadingLamp[] readingLamps;
    private InteriorLamp[] interiorLamps;

    /**
     * @return The front exterior light state.
     */
    @Nullable public FrontExteriorLightState.Value getFrontExteriorLightState() {
        return frontExteriorLightState.getValue();
    }

    /**
     * @return Rear exterior light state.
     */
    @Nullable public Boolean getRearExteriorLightActive() {
        return rearExteriorLightActive != null ? rearExteriorLightActive.getValue() : null;
    }

    /**
     * @return Ambient color, in rgba values from 0-255.
     */
    @Nullable public int[] getAmbientColor() {
        return ambientColor.getValue();
    }

    /**
     * @return The fog lights.
     */
    public FogLight[] getFogLights() {
        return fogLights;
    }

    /**
     * Get the fog light at a location.
     *
     * @return The fog light.
     */
    @Nullable public FogLight getFogLight(LightLocation location) {
        for (FogLight fogLight : fogLights) {
            if (fogLight.getValue() != null && fogLight.getValue().getLocation() == location)
                return fogLight;
        }

        return null;
    }

    /**
     * @return The reading lamps.
     */
    public ReadingLamp[] getReadingLamps() {
        return readingLamps;
    }

    /**
     * Get the reading lamp at a location.
     *
     * @return The reading lamp.
     */
    @Nullable public ReadingLamp getReadingLamp(Location location) {
        for (ReadingLamp readingLamp : readingLamps) {
            if (readingLamp.getValue() != null && readingLamp.getValue().getLocation() == location)
                return readingLamp;
        }

        return null;
    }

    /**
     * @return The interior lamps.
     */
    public InteriorLamp[] getInteriorLamps() {
        return interiorLamps;
    }

    /**
     * Get the interior lamp at a location.
     *
     * @param location The lamp location.
     * @return The interior lamp.
     */
    @Nullable public InteriorLamp getInteriorLamp(LightLocation location) {
        for (InteriorLamp interiorLamp : interiorLamps) {
            if (interiorLamp.getValue() != null && interiorLamp.getValue().getLocation() == location)
                return interiorLamp;
        }
        return null;
    }

    /**
     * Set the lights state. The result is sent through the Lights State command.
     *
     * @param frontExteriorLightState Front exterior light state.
     * @param rearExteriorLightActive Rear exterior light state.
     * @param ambientColor            Ambient color with rgb values from 0-255.
     * @param fogLights               The fog lights.
     * @param readingLamps            The reading lamps.
     * @param interiorLamps           The interior lamps.
     */
    public ControlLights(@Nullable FrontExteriorLightState.Value frontExteriorLightState,
                         @Nullable Boolean rearExteriorLightActive,
                         @Nullable int[] ambientColor,
                         @Nullable FogLight[] fogLights,
                         @Nullable ReadingLamp[] readingLamps,
                         @Nullable InteriorLamp[] interiorLamps) {
        super(TYPE);

        List<Property> properties = new ArrayList<>();

        if (frontExteriorLightState != null) {
            properties.add(this.frontExteriorLightState.update(frontExteriorLightState));
        }

        if (rearExteriorLightActive != null) {
            properties.add(this.rearExteriorLightActive.setValue(rearExteriorLightActive));
        }

        if (ambientColor != null) {
            properties.add(this.ambientColor.update(ambientColor));
        }

        if (fogLights != null) {
            for (FogLight light : fogLights) {
                light.setIdentifier(IDENTIFIER_FOG_LIGHT);
                properties.add(light);
            }

            this.fogLights = fogLights;
        }

        if (readingLamps != null) {
            for (ReadingLamp light : readingLamps) {
                light.setIdentifier(IDENTIFIER_READING_LAMP);
                properties.add(light);
            }

            this.readingLamps = readingLamps;
        }

        if (interiorLamps != null) {
            for (InteriorLamp light : interiorLamps) {
                light.setIdentifier(IDENTIFIER_INTERIOR_LAMP);
                properties.add(light);
            }

            this.interiorLamps = interiorLamps;
        }

        createBytes(properties);
    }

    ControlLights(byte[] bytes) {
        super(bytes);

        ArrayList<FogLight> fogLights = new ArrayList<>();
        ArrayList<InteriorLamp> interiorLamps = new ArrayList<>();
        ArrayList<ReadingLamp> readingLamps = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_FRONT_EXTERIOR_LIGHT_STATE:
                        return frontExteriorLightState.update(p);
                    case IDENTIFIER_REAR_EXTERIOR_LIGHT_ACTIVE:
                        return rearExteriorLightActive.update(p);
                    case IDENTIFIER_AMBIENT_COLOR:
                        return ambientColor.update(p);
                    case IDENTIFIER_FOG_LIGHT:
                        FogLight fogLight = new FogLight(p);
                        fogLights.add(fogLight);
                        return fogLight;
                    case IDENTIFIER_READING_LAMP:
                        ReadingLamp readingLamp = new ReadingLamp(p);
                        readingLamps.add(readingLamp);
                        return readingLamp;
                    case IDENTIFIER_INTERIOR_LAMP:
                        InteriorLamp interiorLamp = new InteriorLamp(p);
                        interiorLamps.add(interiorLamp);
                        return interiorLamp;
                }

                return null;
            });

            this.fogLights = fogLights.toArray(new FogLight[0]);
            this.readingLamps = readingLamps.toArray(new ReadingLamp[0]);
            this.interiorLamps = interiorLamps.toArray(new InteriorLamp[0]);
        }
    }
}
