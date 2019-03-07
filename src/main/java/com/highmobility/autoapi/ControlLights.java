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

import com.highmobility.autoapi.property.Color;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.lights.FogLight;
import com.highmobility.autoapi.property.lights.FrontExteriorLightState;
import com.highmobility.autoapi.property.lights.InteriorLamp;
import com.highmobility.autoapi.property.lights.LightLocation;
import com.highmobility.autoapi.property.lights.ReadingLamp;
import com.highmobility.autoapi.property.value.Location;

import java.util.ArrayList;

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

    private Property<FrontExteriorLightState> frontExteriorLightState =
            new Property(FrontExteriorLightState.class, IDENTIFIER_FRONT_EXTERIOR_LIGHT_STATE);
    private Property<Boolean> rearExteriorLightActive =
            new Property(Boolean.class, IDENTIFIER_REAR_EXTERIOR_LIGHT_ACTIVE);
    private Property<Color> ambientColor = new Property(Color.class, IDENTIFIER_AMBIENT_COLOR);

    private Property<FogLight>[] fogLights;
    private Property<ReadingLamp>[] readingLamps;
    private Property<InteriorLamp>[] interiorLamps;

    /**
     * @return The front exterior light state.
     */
    @Nullable public Property<FrontExteriorLightState> getFrontExteriorLightState() {
        return frontExteriorLightState;
    }

    /**
     * @return Rear exterior light state.
     */
    @Nullable public Property<Boolean> getRearExteriorLightActive() {
        return rearExteriorLightActive;
    }

    /**
     * @return Ambient color, in rgba values from 0-255.
     */
    @Nullable public Property<Color> getAmbientColor() {
        return ambientColor;
    }

    /**
     * @return The fog lights.
     */
    public Property<FogLight>[] getFogLights() {
        return fogLights;
    }

    /**
     * Get the fog light at a location.
     *
     * @param location The light location.
     * @return The fog light.
     */
    @Nullable public Property<FogLight> getFogLight(LightLocation location) {
        for (Property<FogLight> fogLight : fogLights) {
            if (fogLight.getValue() != null && fogLight.getValue().getLocation() == location)
                return fogLight;
        }

        return null;
    }

    /**
     * @return The reading lamps.
     */
    public Property<ReadingLamp>[] getReadingLamps() {
        return readingLamps;
    }

    /**
     * Get the reading lamp at a location.
     *
     * @param location The light location.
     * @return The reading lamp.
     */
    @Nullable public Property<ReadingLamp> getReadingLamp(Location location) {
        for (Property<ReadingLamp> readingLamp : readingLamps) {
            if (readingLamp.getValue() != null && readingLamp.getValue().getLocation() == location)
                return readingLamp;
        }

        return null;
    }

    /**
     * @return The interior lamps.
     */
    public Property<InteriorLamp>[] getInteriorLamps() {
        return interiorLamps;
    }

    /**
     * Get the interior lamp at a location.
     *
     * @param location The lamp location.
     * @return The interior lamp.
     */
    @Nullable public Property<InteriorLamp> getInteriorLamp(LightLocation location) {
        for (Property<InteriorLamp> interiorLamp : interiorLamps) {
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
     * @param ambientColor            Ambient color with rgba values from 0-255.
     * @param fogLights               The fog lights.
     * @param readingLamps            The reading lamps.
     * @param interiorLamps           The interior lamps.
     */
    public ControlLights(@Nullable FrontExteriorLightState frontExteriorLightState,
                         @Nullable Boolean rearExteriorLightActive,
                         @Nullable Color ambientColor,
                         @Nullable FogLight[] fogLights,
                         @Nullable ReadingLamp[] readingLamps,
                         @Nullable InteriorLamp[] interiorLamps) {
        super(TYPE);

        ArrayList<Property> properties = new ArrayList<>();
        ArrayList<Property> fogLightsBuilder = new ArrayList<>();
        ArrayList<Property> readingLampsBuilder = new ArrayList<>();
        ArrayList<Property> interiorLampsBuilder = new ArrayList<>();

        if (frontExteriorLightState != null) {
            properties.add(this.frontExteriorLightState.update(frontExteriorLightState));
        }

        if (rearExteriorLightActive != null) {
            properties.add(this.rearExteriorLightActive.update(rearExteriorLightActive));
        }

        if (ambientColor != null) {
            properties.add(this.ambientColor.update(ambientColor));
        }

        for (FogLight fogLight : fogLights) {
            Property prop = new Property(IDENTIFIER_FOG_LIGHT, fogLight);
            fogLightsBuilder.add(prop);
            properties.add(prop);
        }

        for (ReadingLamp readingLamp : readingLamps) {
            Property<ReadingLamp> prop = new Property<>(IDENTIFIER_READING_LAMP, readingLamp);
            readingLampsBuilder.add(prop);
            properties.add(prop);
        }

        for (InteriorLamp interiorLamp : interiorLamps) {
            Property<InteriorLamp> prop = new Property<>(IDENTIFIER_INTERIOR_LAMP, interiorLamp);
            interiorLampsBuilder.add(prop);
            properties.add(prop);
        }

        this.fogLights = fogLightsBuilder.toArray(new Property[0]);
        this.readingLamps = readingLampsBuilder.toArray(new Property[0]);
        this.interiorLamps = interiorLampsBuilder.toArray(new Property[0]);

        createBytes(properties);
    }

    ControlLights(byte[] bytes) {
        super(bytes);

        ArrayList<Property<FogLight>> fogLights = new ArrayList<>();
        ArrayList<Property<InteriorLamp>> interiorLamps = new ArrayList<>();
        ArrayList<Property<ReadingLamp>> readingLamps = new ArrayList<>();

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
                        Property<FogLight> fogLight = new Property<>(FogLight.class, p);
                        fogLights.add(fogLight);
                        return fogLight;
                    case IDENTIFIER_READING_LAMP:
                        Property<ReadingLamp> readingLamp =
                                new Property<>(ReadingLamp.class, p);
                        readingLamps.add(readingLamp);
                        return readingLamp;
                    case IDENTIFIER_INTERIOR_LAMP:
                        Property<InteriorLamp> interiorLamp =
                                new Property<>(InteriorLamp.class, p);
                        interiorLamps.add(interiorLamp);
                        return interiorLamp;
                }

                return null;
            });
        }

        this.fogLights = fogLights.toArray(new Property[0]);
        this.readingLamps = readingLamps.toArray(new Property[0]);
        this.interiorLamps = interiorLamps.toArray(new Property[0]);
    }
}
