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
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.RgbColour;
import com.highmobility.autoapi.value.Light;
import com.highmobility.autoapi.value.ReadingLamp;
import javax.annotation.Nullable;
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.LocationLongitudinal;
import java.util.ArrayList;
import java.util.List;

/**
 * The lights state
 */
public class LightsState extends SetCommand {
    public static final Identifier identifier = Identifier.LIGHTS;

    Property<FrontExteriorLight> frontExteriorLight = new Property(FrontExteriorLight.class, 0x01);
    Property<ActiveState> rearExteriorLight = new Property(ActiveState.class, 0x02);
    Property<RgbColour> ambientLightColour = new Property(RgbColour.class, 0x04);
    Property<ActiveState> reverseLight = new Property(ActiveState.class, 0x05);
    Property<ActiveState> emergencyBrakeLight = new Property(ActiveState.class, 0x06);
    Property<Light>[] fogLights;
    Property<ReadingLamp>[] readingLamps;
    Property<Light>[] interiorLights;

    /**
     * @return The front exterior light
     */
    public Property<FrontExteriorLight> getFrontExteriorLight() {
        return frontExteriorLight;
    }

    /**
     * @return The rear exterior light
     */
    public Property<ActiveState> getRearExteriorLight() {
        return rearExteriorLight;
    }

    /**
     * @return The ambient light colour
     */
    public Property<RgbColour> getAmbientLightColour() {
        return ambientLightColour;
    }

    /**
     * @return The reverse light
     */
    public Property<ActiveState> getReverseLight() {
        return reverseLight;
    }

    /**
     * @return The emergency brake light
     */
    public Property<ActiveState> getEmergencyBrakeLight() {
        return emergencyBrakeLight;
    }

    /**
     * @return The fog lights
     */
    public Property<Light>[] getFogLights() {
        return fogLights;
    }

    /**
     * @return The reading lamps
     */
    public Property<ReadingLamp>[] getReadingLamps() {
        return readingLamps;
    }

    /**
     * @return The interior lights
     */
    public Property<Light>[] getInteriorLights() {
        return interiorLights;
    }

    /**
     * Get the interior light at a location.
     *
     * @param location The light location.
     * @return The interior light.
     */
    @Nullable public Property<Light> getInteriorLight(LocationLongitudinal location) {
        for (Property<Light> interiorLight : interiorLights) {
            if (interiorLight.getValue() != null && interiorLight.getValue().getLocationLongitudinal() == location)
                return interiorLight;
        }
        return null;
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
     * Get the fog light at a location.
     *
     * @param location The light location.
     * @return The fog light.
     */
    @Nullable public Property<Light> getFogLight(LocationLongitudinal location) {
        for (Property<Light> fogLight : fogLights) {
            if (fogLight.getValue() != null && fogLight.getValue().getLocationLongitudinal() == location)
                return fogLight;
        }

        return null;
    }

    LightsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> fogLightsBuilder = new ArrayList<>();
        ArrayList<Property> readingLampsBuilder = new ArrayList<>();
        ArrayList<Property> interiorLightsBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return frontExteriorLight.update(p);
                    case 0x02: return rearExteriorLight.update(p);
                    case 0x04: return ambientLightColour.update(p);
                    case 0x05: return reverseLight.update(p);
                    case 0x06: return emergencyBrakeLight.update(p);
                    case 0x07:
                        Property<Light> fogLight = new Property(Light.class, p);
                        fogLightsBuilder.add(fogLight);
                        return fogLight;
                    case 0x08:
                        Property<ReadingLamp> readingLamp = new Property(ReadingLamp.class, p);
                        readingLampsBuilder.add(readingLamp);
                        return readingLamp;
                    case 0x09:
                        Property<Light> interiorLight = new Property(Light.class, p);
                        interiorLightsBuilder.add(interiorLight);
                        return interiorLight;
                }

                return null;
            });
        }

        fogLights = fogLightsBuilder.toArray(new Property[0]);
        readingLamps = readingLampsBuilder.toArray(new Property[0]);
        interiorLights = interiorLightsBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private LightsState(Builder builder) {
        super(builder);

        frontExteriorLight = builder.frontExteriorLight;
        rearExteriorLight = builder.rearExteriorLight;
        ambientLightColour = builder.ambientLightColour;
        reverseLight = builder.reverseLight;
        emergencyBrakeLight = builder.emergencyBrakeLight;
        fogLights = builder.fogLights.toArray(new Property[0]);
        readingLamps = builder.readingLamps.toArray(new Property[0]);
        interiorLights = builder.interiorLights.toArray(new Property[0]);
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<FrontExteriorLight> frontExteriorLight;
        private Property<ActiveState> rearExteriorLight;
        private Property<RgbColour> ambientLightColour;
        private Property<ActiveState> reverseLight;
        private Property<ActiveState> emergencyBrakeLight;
        private List<Property> fogLights = new ArrayList<>();
        private List<Property> readingLamps = new ArrayList<>();
        private List<Property> interiorLights = new ArrayList<>();

        public Builder() {
            super(identifier);
        }

        public LightsState build() {
            return new LightsState(this);
        }

        /**
         * @param frontExteriorLight The front exterior light
         * @return The builder
         */
        public Builder setFrontExteriorLight(Property<FrontExteriorLight> frontExteriorLight) {
            this.frontExteriorLight = frontExteriorLight.setIdentifier(0x01);
            addProperty(this.frontExteriorLight);
            return this;
        }
        
        /**
         * @param rearExteriorLight The rear exterior light
         * @return The builder
         */
        public Builder setRearExteriorLight(Property<ActiveState> rearExteriorLight) {
            this.rearExteriorLight = rearExteriorLight.setIdentifier(0x02);
            addProperty(this.rearExteriorLight);
            return this;
        }
        
        /**
         * @param ambientLightColour The ambient light colour
         * @return The builder
         */
        public Builder setAmbientLightColour(Property<RgbColour> ambientLightColour) {
            this.ambientLightColour = ambientLightColour.setIdentifier(0x04);
            addProperty(this.ambientLightColour);
            return this;
        }
        
        /**
         * @param reverseLight The reverse light
         * @return The builder
         */
        public Builder setReverseLight(Property<ActiveState> reverseLight) {
            this.reverseLight = reverseLight.setIdentifier(0x05);
            addProperty(this.reverseLight);
            return this;
        }
        
        /**
         * @param emergencyBrakeLight The emergency brake light
         * @return The builder
         */
        public Builder setEmergencyBrakeLight(Property<ActiveState> emergencyBrakeLight) {
            this.emergencyBrakeLight = emergencyBrakeLight.setIdentifier(0x06);
            addProperty(this.emergencyBrakeLight);
            return this;
        }
        
        /**
         * Add an array of fog lights.
         * 
         * @param fogLights The fog lights
         * @return The builder
         */
        public Builder setFogLights(Property<Light>[] fogLights) {
            this.fogLights.clear();
            for (int i = 0; i < fogLights.length; i++) {
                addFogLight(fogLights[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single fog light.
         * 
         * @param fogLight The fog light
         * @return The builder
         */
        public Builder addFogLight(Property<Light> fogLight) {
            fogLight.setIdentifier(0x07);
            addProperty(fogLight);
            fogLights.add(fogLight);
            return this;
        }
        
        /**
         * Add an array of reading lamps.
         * 
         * @param readingLamps The reading lamps
         * @return The builder
         */
        public Builder setReadingLamps(Property<ReadingLamp>[] readingLamps) {
            this.readingLamps.clear();
            for (int i = 0; i < readingLamps.length; i++) {
                addReadingLamp(readingLamps[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single reading lamp.
         * 
         * @param readingLamp The reading lamp
         * @return The builder
         */
        public Builder addReadingLamp(Property<ReadingLamp> readingLamp) {
            readingLamp.setIdentifier(0x08);
            addProperty(readingLamp);
            readingLamps.add(readingLamp);
            return this;
        }
        
        /**
         * Add an array of interior lights.
         * 
         * @param interiorLights The interior lights
         * @return The builder
         */
        public Builder setInteriorLights(Property<Light>[] interiorLights) {
            this.interiorLights.clear();
            for (int i = 0; i < interiorLights.length; i++) {
                addInteriorLight(interiorLights[i]);
            }
        
            return this;
        }
        /**
         * Add a single interior light.
         * 
         * @param interiorLight The interior light
         * @return The builder
         */
        public Builder addInteriorLight(Property<Light> interiorLight) {
            interiorLight.setIdentifier(0x09);
            addProperty(interiorLight);
            interiorLights.add(interiorLight);
            return this;
        }
    }

    public enum FrontExteriorLight implements ByteEnum {
        INACTIVE((byte) 0x00),
        ACTIVE((byte) 0x01),
        ACTIVE_WITH_FULL_BEAM((byte) 0x02),
        DLR((byte) 0x03),
        AUTOMATIC((byte) 0x04);
    
        public static FrontExteriorLight fromByte(byte byteValue) throws CommandParseException {
            FrontExteriorLight[] values = FrontExteriorLight.values();
    
            for (int i = 0; i < values.length; i++) {
                FrontExteriorLight state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        FrontExteriorLight(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}