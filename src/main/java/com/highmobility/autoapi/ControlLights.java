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
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.lights.FogLight;
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

    private static final byte FRONT_EXTERIOR_LIGHT_STATE_IDENTIFIER = 0x01;
    private static final byte REAR_EXTERIOR_LIGHT_ACTIVE_IDENTIFIER = 0x02;
    private static final byte AMBIENT_COLOR_IDENTIFIER = 0x04;

    private static final byte IDENTIFIER_FOG_LIGHTS = 0x07;
    private static final byte IDENTIFIER_READING_LAMPS = 0x08;
    private static final byte IDENTIFIER_INTERIOR_LAMPS = 0x09;

    private FrontExteriorLightState frontExteriorLightState;
    private Boolean rearExteriorLightActive;
    private int[] ambientColor;

    private FogLight[] fogLights;
    private ReadingLamp[] readingLamps;
    private InteriorLamp[] interiorLamps;

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
     * @return Ambient color, in rgba values from 0-255.
     */
    public int[] getAmbientColor() {
        return ambientColor;
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
     * @param location The light location.
     * @return The fog light.
     */
    @Nullable public FogLight getFogLight(LightLocation location) {
        for (FogLight fogLight : fogLights) {
            if (fogLight.getLocation() == location) return fogLight;
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
     * @param location The light location.
     * @return The reading lamp.
     */
    @Nullable public ReadingLamp getReadingLamp(Location location) {
        for (ReadingLamp readingLamp : readingLamps) {
            if (readingLamp.getLocation() == location) return readingLamp;
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
            if (interiorLamp.getLocation() == location) return interiorLamp;
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
     * @param interiorLamps           The interior lights.
     * @param readingLamps            The reading lamps.
     */
    public ControlLights(@Nullable FrontExteriorLightState frontExteriorLightState,
                         @Nullable Boolean rearExteriorLightActive,
                         @Nullable int[] ambientColor,
                         @Nullable FogLight[] fogLights,
                         @Nullable ReadingLamp[] readingLamps,
                         @Nullable InteriorLamp[] interiorLamps) {
        super(TYPE, getProperties(frontExteriorLightState, rearExteriorLightActive, ambientColor,
                fogLights, readingLamps, interiorLamps));

        this.frontExteriorLightState = frontExteriorLightState;
        this.rearExteriorLightActive = rearExteriorLightActive;
        this.ambientColor = ambientColor;
        this.fogLights = fogLights;
        this.readingLamps = readingLamps;
        this.interiorLamps = interiorLamps;
    }

    static Property[] getProperties(@Nullable FrontExteriorLightState frontExteriorLightState,
                                    @Nullable Boolean rearExteriorLightActive,
                                    @Nullable int[] ambientColor,
                                    @Nullable FogLight[] fogLights,
                                    @Nullable ReadingLamp[] readingLamps,
                                    @Nullable InteriorLamp[] interiorLamps) {
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

        if (ambientColor != null) {
            ColorProperty prop = new ColorProperty(AMBIENT_COLOR_IDENTIFIER, ambientColor);
            properties.add(prop);
        }

        if (fogLights != null) for (FogLight light : fogLights) {
            light.setIdentifier(IDENTIFIER_FOG_LIGHTS);
            properties.add(light);
        }

        if (readingLamps != null) for (ReadingLamp light : readingLamps) {
            light.setIdentifier(IDENTIFIER_READING_LAMPS);
            properties.add(light);
        }
        if (interiorLamps != null) for (InteriorLamp light : interiorLamps) {
            light.setIdentifier(IDENTIFIER_INTERIOR_LAMPS);
            properties.add(light);
        }

        return properties.toArray(new Property[0]);
    }

    ControlLights(byte[] bytes) {
        super(bytes);

        ArrayList<FogLight> fogLights = new ArrayList<>();
        ArrayList<InteriorLamp> interiorLamps = new ArrayList<>();
        ArrayList<ReadingLamp> readingLamps = new ArrayList<>();

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case FRONT_EXTERIOR_LIGHT_STATE_IDENTIFIER:
                        frontExteriorLightState =
                                FrontExteriorLightState.fromByte(p.getValueByte());
                        return frontExteriorLightState;
                    case REAR_EXTERIOR_LIGHT_ACTIVE_IDENTIFIER:
                        rearExteriorLightActive = Property.getBool(p.getValueByte());
                        return rearExteriorLightActive;
                    case AMBIENT_COLOR_IDENTIFIER:
                        ambientColor = new ColorProperty(p.getPropertyBytes()).getAmbientColor();
                        return ambientColor;
                    case IDENTIFIER_FOG_LIGHTS:
                        FogLight fogLight = new FogLight(p);
                        fogLights.add(fogLight);
                        return fogLight;
                    case IDENTIFIER_READING_LAMPS:
                        ReadingLamp readingLamp = new ReadingLamp(p);
                        readingLamps.add(readingLamp);
                        return readingLamp;
                    case IDENTIFIER_INTERIOR_LAMPS:
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
