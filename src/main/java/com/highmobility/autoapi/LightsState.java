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
import com.highmobility.autoapi.property.FrontExteriorLightState;
import com.highmobility.autoapi.property.Property;

import javax.annotation.Nullable;

/**
 * This command is sent when a Get Lights State message is received by the car.
 */
public class LightsState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.LIGHTS, 0x01);

    private static final byte REAR_EXTERIOR_LIGHT_ACTIVE_IDENTIFIER = 0x02;
    private static final byte INTERIOR_LIGHT_ACTIVE_IDENTIFIER = 0x03;
    private static final byte AMBIENT_COLOR_IDENTIFIER = 0x04;
    private static final byte REVERSE_LIGHT_IDENTIFIER = 0x05;
    private static final byte EMERGENCY_BRAKE_LIGHT_IDENTIFIER = 0x06;
    private static final byte IDENTIFIER_FRONT_EXTERIOR = 0x01;

    FrontExteriorLightState frontExteriorLightState;
    Boolean rearExteriorLightActive;
    Boolean interiorLightActive;
    int[] ambientColor;

    // l7
    Boolean reverseLightActive;
    Boolean emergencyBrakeLightActive;

    /**
     * @return The front exterior light state.
     */
    @Nullable public FrontExteriorLightState getFrontExteriorLightState() {
        return frontExteriorLightState;
    }

    /**
     * @return The rear exterior light state.
     */
    @Nullable public Boolean isRearExteriorLightActive() {
        return rearExteriorLightActive;
    }

    /**
     * @return The interior light state.
     */
    @Nullable public Boolean isInteriorLightActive() {
        return interiorLightActive;
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
    @Nullable public Boolean isReverseLightActive() {
        return reverseLightActive;
    }

    /**
     * @return The emergency brake light state.
     */
    @Nullable public Boolean isEmergencyBrakeLightActive() {
        return emergencyBrakeLightActive;
    }

    public LightsState(byte[] bytes) {
        super(bytes);

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(property -> {
                switch (property.getPropertyIdentifier()) {
                    case IDENTIFIER_FRONT_EXTERIOR:
                        frontExteriorLightState = FrontExteriorLightState.fromByte(property
                                .getValueByte());
                        break;
                    case REAR_EXTERIOR_LIGHT_ACTIVE_IDENTIFIER:
                        rearExteriorLightActive = Property.getBool(property.getValueByte());
                        break;
                    case INTERIOR_LIGHT_ACTIVE_IDENTIFIER:
                        interiorLightActive = Property.getBool(property.getValueByte());
                        break;
                    case AMBIENT_COLOR_IDENTIFIER:
                        byte[] valueBytes = property.getValueBytes();
                        if (valueBytes.length != 3) throw new CommandParseException();

                        ambientColor = new int[4];
                        ambientColor[0] = valueBytes[0] & 0xFF;
                        ambientColor[1] = valueBytes[1] & 0xFF;
                        ambientColor[2] = valueBytes[2] & 0xFF;
                        ambientColor[3] = 255;

                        break;
                    case REVERSE_LIGHT_IDENTIFIER:
                        reverseLightActive = Property.getBool(property.getValueByte());
                        break;

                    case EMERGENCY_BRAKE_LIGHT_IDENTIFIER:
                        emergencyBrakeLightActive = Property.getBool(property.getValueByte());
                        break;
                }
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private LightsState(Builder builder) {
        super(builder);
        frontExteriorLightState = builder.frontExteriorLightState;
        rearExteriorLightActive = builder.rearExteriorLightActive;
        interiorLightActive = builder.interiorLightActive;
        ambientColor = builder.ambientColor;
        reverseLightActive = builder.reverseLightActive;
        emergencyBrakeLightActive = builder.emergencyBrakeLightActive;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private FrontExteriorLightState frontExteriorLightState;
        private Boolean rearExteriorLightActive;
        private Boolean interiorLightActive;
        private int[] ambientColor;

        private boolean reverseLightActive;
        private boolean emergencyBrakeLightActive;

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
        public Builder setRearExteriorLightActive(Boolean rearExteriorLightActive) {
            this.rearExteriorLightActive = rearExteriorLightActive;
            addProperty(new BooleanProperty(REAR_EXTERIOR_LIGHT_ACTIVE_IDENTIFIER,
                    rearExteriorLightActive));
            return this;
        }

        /**
         * @param interiorLightActive Whether interior lights are active.
         * @return The builder.
         */
        public Builder setInteriorLightActive(Boolean interiorLightActive) {
            this.interiorLightActive = interiorLightActive;
            addProperty(new BooleanProperty(INTERIOR_LIGHT_ACTIVE_IDENTIFIER, interiorLightActive));
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
        public Builder setReverseLightActive(boolean reverseLightActive) {
            this.reverseLightActive = reverseLightActive;
            addProperty(new BooleanProperty(REVERSE_LIGHT_IDENTIFIER, reverseLightActive));
            return this;
        }

        /**
         * @param emergencyBrakeLightActive The emergency brake light state.
         * @return The builder.
         */
        public Builder setEmergencyBrakeLightActive(boolean emergencyBrakeLightActive) {
            this.emergencyBrakeLightActive = emergencyBrakeLightActive;
            addProperty(new BooleanProperty(EMERGENCY_BRAKE_LIGHT_IDENTIFIER,
                    emergencyBrakeLightActive));
            return this;
        }

        public LightsState build() {
            return new LightsState(this);
        }
    }
}