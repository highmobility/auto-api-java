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
import com.highmobility.autoapi.property.ColorProperty;
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
 * This command is sent when a Get Lights State message is received by the car.
 */
public class LightsState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.LIGHTS, 0x01);

    private static final byte REAR_EXTERIOR_LIGHT_ACTIVE_IDENTIFIER = 0x02;
    private static final byte AMBIENT_COLOR_IDENTIFIER = 0x04;
    private static final byte REVERSE_LIGHT_IDENTIFIER = 0x05;
    private static final byte EMERGENCY_BRAKE_LIGHT_IDENTIFIER = 0x06;
    private static final byte IDENTIFIER_FRONT_EXTERIOR = 0x01;

    private static final byte IDENTIFIER_FOG_LIGHTS = 0x07;
    private static final byte IDENTIFIER_READING_LAMPS = 0x08;
    private static final byte IDENTIFIER_INTERIOR_LAMPS = 0x09;

    FrontExteriorLightState frontExteriorLightState;
    BooleanProperty rearExteriorLightActive;
    int[] ambientColor;

    // l7
    BooleanProperty reverseLightActive;
    BooleanProperty emergencyBrakeLightActive;

    // l9
    FogLight[] fogLights;
    ReadingLamp[] readingLamps;
    InteriorLamp[] interiorLamps;

    /**
     * @return The front exterior light state.
     */
    @Nullable public FrontExteriorLightState getFrontExteriorLightState() {
        return frontExteriorLightState;
    }

    /**
     * @return The rear exterior light state.
     */
    @Nullable public BooleanProperty isRearExteriorLightActive() {
        return rearExteriorLightActive;
    }

    /**
     * @return The ambient color, in rgba values from 0-255.
     */
    @Nullable public int[] getAmbientColor() {
        return ambientColor;
    }

    /**
     * @return The reverse light state.
     */
    @Nullable public BooleanProperty isReverseLightActive() {
        return reverseLightActive;
    }

    /**
     * @return The emergency brake light state.
     */
    @Nullable public BooleanProperty isEmergencyBrakeLightActive() {
        return emergencyBrakeLightActive;
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

    LightsState(byte[] bytes) {
        super(bytes);

        ArrayList<FogLight> fogLights = new ArrayList<>();
        ArrayList<InteriorLamp> interiorLamps = new ArrayList<>();
        ArrayList<ReadingLamp> readingLamps = new ArrayList<>();

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_FRONT_EXTERIOR:
                        frontExteriorLightState =
                                FrontExteriorLightState.fromByte(p.getValueByte());
                        return frontExteriorLightState;
                    case REAR_EXTERIOR_LIGHT_ACTIVE_IDENTIFIER:
                        rearExteriorLightActive = new BooleanProperty(p);
                        return rearExteriorLightActive;
                    case AMBIENT_COLOR_IDENTIFIER:
                        byte[] valueBytes = p.getValueBytes();
                        if (valueBytes.length != 3) throw new CommandParseException();

                        ambientColor = new int[4];
                        ambientColor[0] = valueBytes[0] & 0xFF;
                        ambientColor[1] = valueBytes[1] & 0xFF;
                        ambientColor[2] = valueBytes[2] & 0xFF;
                        ambientColor[3] = 255;

                        return ambientColor;
                    case REVERSE_LIGHT_IDENTIFIER:
                        reverseLightActive = new BooleanProperty(p);
                        return reverseLightActive;
                    case EMERGENCY_BRAKE_LIGHT_IDENTIFIER:
                        emergencyBrakeLightActive = new BooleanProperty(p);
                        return emergencyBrakeLightActive;
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

    @Override public boolean isState() {
        return true;
    }

    private LightsState(Builder builder) {
        super(builder);
        frontExteriorLightState = builder.frontExteriorLightState;
        rearExteriorLightActive = builder.rearExteriorLightActive;
        ambientColor = builder.ambientColor;
        reverseLightActive = builder.reverseLightActive;
        emergencyBrakeLightActive = builder.emergencyBrakeLightActive;
        fogLights = builder.fogLights.toArray(new FogLight[0]);
        readingLamps = builder.readingLamps.toArray(new ReadingLamp[0]);
        interiorLamps = builder.interiorLamps.toArray(new InteriorLamp[0]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private FrontExteriorLightState frontExteriorLightState;
        private BooleanProperty rearExteriorLightActive;
        private BooleanProperty interiorLightActive;
        private int[] ambientColor;

        private BooleanProperty reverseLightActive;
        private BooleanProperty emergencyBrakeLightActive;

        private ArrayList<FogLight> fogLights = new ArrayList<>();
        private ArrayList<ReadingLamp> readingLamps = new ArrayList<>();
        private ArrayList<InteriorLamp> interiorLamps = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        /**
         * @param frontExteriorLightState The front exterior light state.
         * @return The builder.
         */
        public Builder setFrontExteriorLightState(FrontExteriorLightState frontExteriorLightState) {
            this.frontExteriorLightState = frontExteriorLightState;
            addProperty(new Property(IDENTIFIER_FRONT_EXTERIOR, frontExteriorLightState.getByte()));
            return this;
        }

        /**
         * @param rearExteriorLightActive Whether exterior lights are active.
         * @return The builder.
         */
        public Builder setRearExteriorLightActive(BooleanProperty rearExteriorLightActive) {
            this.rearExteriorLightActive = rearExteriorLightActive;
            rearExteriorLightActive.setIdentifier(REAR_EXTERIOR_LIGHT_ACTIVE_IDENTIFIER);
            addProperty(rearExteriorLightActive);
            return this;
        }

        /**
         * @param ambientColor The ambient color, in rgba values from 0-255.
         * @return The builder.
         */
        public Builder setAmbientColor(int[] ambientColor) {
            this.ambientColor = ambientColor;
            addProperty(new ColorProperty(AMBIENT_COLOR_IDENTIFIER, ambientColor));
            return this;
        }

        /**
         * @param reverseLightActive The reverse light state.
         * @return The builder.
         */
        public Builder setReverseLightActive(BooleanProperty reverseLightActive) {
            this.reverseLightActive = reverseLightActive;
            reverseLightActive.setIdentifier(REVERSE_LIGHT_IDENTIFIER);
            addProperty(reverseLightActive);
            return this;
        }

        /**
         * @param emergencyBrakeLightActive The emergency brake light state.
         * @return The builder.
         */
        public Builder setEmergencyBrakeLightActive(BooleanProperty emergencyBrakeLightActive) {
            this.emergencyBrakeLightActive = emergencyBrakeLightActive;
            emergencyBrakeLightActive.setIdentifier(EMERGENCY_BRAKE_LIGHT_IDENTIFIER);
            addProperty(emergencyBrakeLightActive);
            return this;
        }

        /**
         * Add a fog light.
         *
         * @param fogLight The fog light.
         * @return The builder.
         */
        public Builder addFogLight(FogLight fogLight) {
            this.fogLights.add(fogLight);
            fogLight.setIdentifier(IDENTIFIER_FOG_LIGHTS);
            addProperty(fogLight);
            return this;
        }

        /**
         * @param fogLights The lights.
         * @return The builder.
         */
        public Builder setFogLights(FogLight[] fogLights) {
            this.fogLights.clear();

            for (FogLight lamp : fogLights) {
                addFogLight(lamp);
            }

            return this;
        }

        /**
         * Add a reading lamp.
         *
         * @param readingLamp The fog light.
         * @return The builder.
         */
        public Builder addReadingLamp(ReadingLamp readingLamp) {
            this.readingLamps.add(readingLamp);
            readingLamp.setIdentifier(IDENTIFIER_READING_LAMPS);
            addProperty(readingLamp);
            return this;
        }

        /**
         * @param readingLamps The lights.
         * @return The builder.
         */
        public Builder setReadingLamps(ReadingLamp[] readingLamps) {
            this.readingLamps.clear();

            for (ReadingLamp lamp : readingLamps) {
                addReadingLamp(lamp);
            }

            return this;
        }

        /**
         * Add a interior lamp.
         *
         * @param interiorLamp The fog light.
         * @return The builder.
         */
        public Builder addInteriorLamp(InteriorLamp interiorLamp) {
            this.interiorLamps.add(interiorLamp);
            interiorLamp.setIdentifier(IDENTIFIER_INTERIOR_LAMPS);
            addProperty(interiorLamp);
            return this;
        }

        /**
         * @param interiorLamps The lights.
         * @return The builder.
         */
        public Builder setInteriorLamps(InteriorLamp[] interiorLamps) {
            this.interiorLamps.clear();

            for (InteriorLamp lamp : interiorLamps) {
                addInteriorLamp(lamp);
            }

            return this;
        }

        public LightsState build() {
            return new LightsState(this);
        }
    }
}