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

import com.highmobility.autoapi.property.FogLight;
import com.highmobility.autoapi.property.IntegerArrayProperty;
import com.highmobility.autoapi.property.ObjectProperty;
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

    private static final byte IDENTIFIER_FRONT_EXTERIOR_LIGHT_STATE = 0x01;
    private static final byte IDENTIFIER_REAR_EXTERIOR_LIGHT_ACTIVE = 0x02;
    private static final byte IDENTIFIER_AMBIENT_COLOR = 0x04;
    private static final byte IDENTIFIER_REVERSE_LIGHT_ACTIVE = 0x05;
    private static final byte IDENTIFIER_EMERGENCY_BRAKE_LIGHT_ACTIVE = 0x06;

    private static final byte IDENTIFIER_FOG_LIGHTS = 0x07;
    private static final byte IDENTIFIER_READING_LAMPS = 0x08;
    private static final byte IDENTIFIER_INTERIOR_LAMPS = 0x09;

    FrontExteriorLightState frontExteriorLightState =
            new FrontExteriorLightState(IDENTIFIER_FRONT_EXTERIOR_LIGHT_STATE);
    ObjectProperty<Boolean> rearExteriorLightActive =
            new ObjectProperty<>(Boolean.class, IDENTIFIER_REAR_EXTERIOR_LIGHT_ACTIVE);

    IntegerArrayProperty ambientColor = new IntegerArrayProperty(IDENTIFIER_AMBIENT_COLOR);

    // l7
    ObjectProperty<Boolean> reverseLightActive = new ObjectProperty<>(Boolean.class, IDENTIFIER_REVERSE_LIGHT_ACTIVE);
    ObjectProperty<Boolean> emergencyBrakeLightActive =
            new ObjectProperty<>(Boolean.class, IDENTIFIER_EMERGENCY_BRAKE_LIGHT_ACTIVE);

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
    @Nullable public ObjectProperty<Boolean> isRearExteriorLightActive() {
        return rearExteriorLightActive;
    }

    /**
     * @return The ambient color, in rgb values.
     */
    @Nullable public IntegerArrayProperty getAmbientColor() {
        return ambientColor;
    }

    /**
     * @return The reverse light state.
     */
    @Nullable public ObjectProperty<Boolean> isReverseLightActive() {
        return reverseLightActive;
    }

    /**
     * @return The emergency brake light state.
     */
    @Nullable public ObjectProperty<Boolean> isEmergencyBrakeLightActive() {
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

    LightsState(byte[] bytes) {
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
                        rearExteriorLightActive = new ObjectProperty<>(Boolean.class, p);
                        return rearExteriorLightActive;
                    case IDENTIFIER_AMBIENT_COLOR:
                        return this.ambientColor.update(p);
                    case IDENTIFIER_REVERSE_LIGHT_ACTIVE:
                        return reverseLightActive.update(p);
                    case IDENTIFIER_EMERGENCY_BRAKE_LIGHT_ACTIVE:
                        return emergencyBrakeLightActive.update(p);
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
        private ObjectProperty<Boolean> rearExteriorLightActive;
        private ObjectProperty<Boolean> interiorLightActive;
        private IntegerArrayProperty ambientColor;

        private ObjectProperty<Boolean> reverseLightActive;
        private ObjectProperty<Boolean> emergencyBrakeLightActive;

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
            addProperty(frontExteriorLightState.setIdentifier(IDENTIFIER_FRONT_EXTERIOR_LIGHT_STATE));
            return this;
        }

        /**
         * @param rearExteriorLightActive Whether exterior lights are active.
         * @return The builder.
         */
        public Builder setRearExteriorLightActive(ObjectProperty<Boolean> rearExteriorLightActive) {
            this.rearExteriorLightActive = rearExteriorLightActive;
            rearExteriorLightActive.setIdentifier(IDENTIFIER_REAR_EXTERIOR_LIGHT_ACTIVE);
            addProperty(rearExteriorLightActive);
            return this;
        }

        /**
         * @param ambientColor The ambient color, in rgba values from 0-255.
         * @return The builder.
         */
        public Builder setAmbientColor(IntegerArrayProperty ambientColor) {
            this.ambientColor = ambientColor;
            addProperty(ambientColor.setIdentifier(IDENTIFIER_AMBIENT_COLOR));
            return this;
        }

        /**
         * @param reverseLightActive The reverse light state.
         * @return The builder.
         */
        public Builder setReverseLightActive(ObjectProperty<Boolean> reverseLightActive) {
            this.reverseLightActive = reverseLightActive;
            reverseLightActive.setIdentifier(IDENTIFIER_REVERSE_LIGHT_ACTIVE);
            addProperty(reverseLightActive);
            return this;
        }

        /**
         * @param emergencyBrakeLightActive The emergency brake light state.
         * @return The builder.
         */
        public Builder setEmergencyBrakeLightActive(ObjectProperty<Boolean> emergencyBrakeLightActive) {
            this.emergencyBrakeLightActive = emergencyBrakeLightActive;
            emergencyBrakeLightActive.setIdentifier(IDENTIFIER_EMERGENCY_BRAKE_LIGHT_ACTIVE);
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