/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import javax.annotation.Nullable;
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.LocationLongitudinal;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.RgbColour;
import com.highmobility.autoapi.value.Light;
import com.highmobility.autoapi.value.ReadingLamp;
import java.util.ArrayList;
import java.util.List;
import com.highmobility.value.Bytes;

import static com.highmobility.utils.ByteUtils.hexFromByte;

/**
 * The Lights capability
 */
public class Lights {
    public static final int IDENTIFIER = Identifier.LIGHTS;

    public static final byte PROPERTY_FRONT_EXTERIOR_LIGHT = 0x01;
    public static final byte PROPERTY_REAR_EXTERIOR_LIGHT = 0x02;
    public static final byte PROPERTY_AMBIENT_LIGHT_COLOUR = 0x04;
    public static final byte PROPERTY_REVERSE_LIGHT = 0x05;
    public static final byte PROPERTY_EMERGENCY_BRAKE_LIGHT = 0x06;
    public static final byte PROPERTY_FOG_LIGHTS = 0x07;
    public static final byte PROPERTY_READING_LAMPS = 0x08;
    public static final byte PROPERTY_INTERIOR_LIGHTS = 0x09;
    public static final byte PROPERTY_SWITCH_POSITION = 0x0a;

    /**
     * Get all lights properties
     */
    public static class GetState extends GetCommand {
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        GetState(byte[] bytes) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific lights properties
     */
    public static class GetProperties extends GetCommand {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The lights state
     */
    public static class State extends SetCommand {
        Property<FrontExteriorLight> frontExteriorLight = new Property(FrontExteriorLight.class, PROPERTY_FRONT_EXTERIOR_LIGHT);
        Property<ActiveState> rearExteriorLight = new Property(ActiveState.class, PROPERTY_REAR_EXTERIOR_LIGHT);
        Property<RgbColour> ambientLightColour = new Property(RgbColour.class, PROPERTY_AMBIENT_LIGHT_COLOUR);
        Property<ActiveState> reverseLight = new Property(ActiveState.class, PROPERTY_REVERSE_LIGHT);
        Property<ActiveState> emergencyBrakeLight = new Property(ActiveState.class, PROPERTY_EMERGENCY_BRAKE_LIGHT);
        Property<Light>[] fogLights;
        Property<ReadingLamp>[] readingLamps;
        Property<Light>[] interiorLights;
        Property<SwitchPosition> switchPosition = new Property(SwitchPosition.class, PROPERTY_SWITCH_POSITION);
    
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
         * @return Position of the rotary light switch
         */
        public Property<SwitchPosition> getSwitchPosition() {
            return switchPosition;
        }
    
        /**
         * Get the interior light at a location.
         *
         * @param location The light location.
         * @return The interior light.
         */
        @Nullable public Property<Light> getInteriorLight(LocationLongitudinal location) {
            for (Property<Light> interiorLight : interiorLights) {
                if (interiorLight.getValue() != null && interiorLight.getValue().getLocation() == location)
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
                if (fogLight.getValue() != null && fogLight.getValue().getLocation() == location)
                    return fogLight;
            }
    
            return null;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            ArrayList<Property> fogLightsBuilder = new ArrayList<>();
            ArrayList<Property> readingLampsBuilder = new ArrayList<>();
            ArrayList<Property> interiorLightsBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_FRONT_EXTERIOR_LIGHT: return frontExteriorLight.update(p);
                        case PROPERTY_REAR_EXTERIOR_LIGHT: return rearExteriorLight.update(p);
                        case PROPERTY_AMBIENT_LIGHT_COLOUR: return ambientLightColour.update(p);
                        case PROPERTY_REVERSE_LIGHT: return reverseLight.update(p);
                        case PROPERTY_EMERGENCY_BRAKE_LIGHT: return emergencyBrakeLight.update(p);
                        case PROPERTY_FOG_LIGHTS:
                            Property<Light> fogLight = new Property(Light.class, p);
                            fogLightsBuilder.add(fogLight);
                            return fogLight;
                        case PROPERTY_READING_LAMPS:
                            Property<ReadingLamp> readingLamp = new Property(ReadingLamp.class, p);
                            readingLampsBuilder.add(readingLamp);
                            return readingLamp;
                        case PROPERTY_INTERIOR_LIGHTS:
                            Property<Light> interiorLight = new Property(Light.class, p);
                            interiorLightsBuilder.add(interiorLight);
                            return interiorLight;
                        case PROPERTY_SWITCH_POSITION: return switchPosition.update(p);
                    }
    
                    return null;
                });
            }
    
            fogLights = fogLightsBuilder.toArray(new Property[0]);
            readingLamps = readingLampsBuilder.toArray(new Property[0]);
            interiorLights = interiorLightsBuilder.toArray(new Property[0]);
        }
    
        private State(Builder builder) {
            super(builder);
    
            frontExteriorLight = builder.frontExteriorLight;
            rearExteriorLight = builder.rearExteriorLight;
            ambientLightColour = builder.ambientLightColour;
            reverseLight = builder.reverseLight;
            emergencyBrakeLight = builder.emergencyBrakeLight;
            fogLights = builder.fogLights.toArray(new Property[0]);
            readingLamps = builder.readingLamps.toArray(new Property[0]);
            interiorLights = builder.interiorLights.toArray(new Property[0]);
            switchPosition = builder.switchPosition;
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
            private Property<SwitchPosition> switchPosition;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param frontExteriorLight The front exterior light
             * @return The builder
             */
            public Builder setFrontExteriorLight(Property<FrontExteriorLight> frontExteriorLight) {
                this.frontExteriorLight = frontExteriorLight.setIdentifier(PROPERTY_FRONT_EXTERIOR_LIGHT);
                addProperty(this.frontExteriorLight);
                return this;
            }
            
            /**
             * @param rearExteriorLight The rear exterior light
             * @return The builder
             */
            public Builder setRearExteriorLight(Property<ActiveState> rearExteriorLight) {
                this.rearExteriorLight = rearExteriorLight.setIdentifier(PROPERTY_REAR_EXTERIOR_LIGHT);
                addProperty(this.rearExteriorLight);
                return this;
            }
            
            /**
             * @param ambientLightColour The ambient light colour
             * @return The builder
             */
            public Builder setAmbientLightColour(Property<RgbColour> ambientLightColour) {
                this.ambientLightColour = ambientLightColour.setIdentifier(PROPERTY_AMBIENT_LIGHT_COLOUR);
                addProperty(this.ambientLightColour);
                return this;
            }
            
            /**
             * @param reverseLight The reverse light
             * @return The builder
             */
            public Builder setReverseLight(Property<ActiveState> reverseLight) {
                this.reverseLight = reverseLight.setIdentifier(PROPERTY_REVERSE_LIGHT);
                addProperty(this.reverseLight);
                return this;
            }
            
            /**
             * @param emergencyBrakeLight The emergency brake light
             * @return The builder
             */
            public Builder setEmergencyBrakeLight(Property<ActiveState> emergencyBrakeLight) {
                this.emergencyBrakeLight = emergencyBrakeLight.setIdentifier(PROPERTY_EMERGENCY_BRAKE_LIGHT);
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
                fogLight.setIdentifier(PROPERTY_FOG_LIGHTS);
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
                readingLamp.setIdentifier(PROPERTY_READING_LAMPS);
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
                interiorLight.setIdentifier(PROPERTY_INTERIOR_LIGHTS);
                addProperty(interiorLight);
                interiorLights.add(interiorLight);
                return this;
            }
            
            /**
             * @param switchPosition Position of the rotary light switch
             * @return The builder
             */
            public Builder setSwitchPosition(Property<SwitchPosition> switchPosition) {
                this.switchPosition = switchPosition.setIdentifier(PROPERTY_SWITCH_POSITION);
                addProperty(this.switchPosition);
                return this;
            }
        }
    }

    /**
     * Control lights
     */
    public static class ControlLights extends SetCommand {
        Property<FrontExteriorLight> frontExteriorLight = new Property(FrontExteriorLight.class, PROPERTY_FRONT_EXTERIOR_LIGHT);
        Property<ActiveState> rearExteriorLight = new Property(ActiveState.class, PROPERTY_REAR_EXTERIOR_LIGHT);
        Property<RgbColour> ambientLightColour = new Property(RgbColour.class, PROPERTY_AMBIENT_LIGHT_COLOUR);
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
         * Control lights
         *
         * @param frontExteriorLight The front exterior light
         * @param rearExteriorLight The rear exterior light
         * @param ambientLightColour The ambient light colour
         * @param fogLights The fog lights
         * @param readingLamps The reading lamps
         * @param interiorLights The interior lights
         */
        public ControlLights(@Nullable FrontExteriorLight frontExteriorLight, @Nullable ActiveState rearExteriorLight, @Nullable RgbColour ambientLightColour, @Nullable Light[] fogLights, @Nullable ReadingLamp[] readingLamps, @Nullable Light[] interiorLights) {
            super(IDENTIFIER);
        
            addProperty(this.frontExteriorLight.update(frontExteriorLight));
            addProperty(this.rearExteriorLight.update(rearExteriorLight));
            addProperty(this.ambientLightColour.update(ambientLightColour));
            ArrayList<Property> fogLightsBuilder = new ArrayList<>();
            if (fogLights != null) {
                for (Light fogLight : fogLights) {
                    Property prop = new Property(0x07, fogLight);
                    fogLightsBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.fogLights = fogLightsBuilder.toArray(new Property[0]);
            
            ArrayList<Property> readingLampsBuilder = new ArrayList<>();
            if (readingLamps != null) {
                for (ReadingLamp readingLamp : readingLamps) {
                    Property prop = new Property(0x08, readingLamp);
                    readingLampsBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.readingLamps = readingLampsBuilder.toArray(new Property[0]);
            
            ArrayList<Property> interiorLightsBuilder = new ArrayList<>();
            if (interiorLights != null) {
                for (Light interiorLight : interiorLights) {
                    Property prop = new Property(0x09, interiorLight);
                    interiorLightsBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.interiorLights = interiorLightsBuilder.toArray(new Property[0]);
            if (this.frontExteriorLight.getValue() == null && this.rearExteriorLight.getValue() == null && this.ambientLightColour.getValue() == null && this.fogLights.length == 0 && this.readingLamps.length == 0 && this.interiorLights.length == 0) throw new IllegalArgumentException();
            createBytes();
        }
    
        ControlLights(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
        
            ArrayList<Property<Light>> fogLightsBuilder = new ArrayList<>();
            ArrayList<Property<ReadingLamp>> readingLampsBuilder = new ArrayList<>();
            ArrayList<Property<Light>> interiorLightsBuilder = new ArrayList<>();
        
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_FRONT_EXTERIOR_LIGHT: return frontExteriorLight.update(p);
                        case PROPERTY_REAR_EXTERIOR_LIGHT: return rearExteriorLight.update(p);
                        case PROPERTY_AMBIENT_LIGHT_COLOUR: return ambientLightColour.update(p);
                        case PROPERTY_FOG_LIGHTS: {
                            Property fogLight = new Property(Light.class, p);
                            fogLightsBuilder.add(fogLight);
                            return fogLight;
                        }
                        case PROPERTY_READING_LAMPS: {
                            Property readingLamp = new Property(ReadingLamp.class, p);
                            readingLampsBuilder.add(readingLamp);
                            return readingLamp;
                        }
                        case PROPERTY_INTERIOR_LIGHTS: {
                            Property interiorLight = new Property(Light.class, p);
                            interiorLightsBuilder.add(interiorLight);
                            return interiorLight;
                        }
                    }
                    return null;
                });
            }
        
            fogLights = fogLightsBuilder.toArray(new Property[0]);
            readingLamps = readingLampsBuilder.toArray(new Property[0]);
            interiorLights = interiorLightsBuilder.toArray(new Property[0]);
            if (this.frontExteriorLight.getValue() == null && this.rearExteriorLight.getValue() == null && this.ambientLightColour.getValue() == null && this.fogLights.length == 0 && this.readingLamps.length == 0 && this.interiorLights.length == 0) throw new NoPropertiesException();
        }
    }

    public enum FrontExteriorLight implements ByteEnum {
        INACTIVE((byte) 0x00),
        ACTIVE((byte) 0x01),
        ACTIVE_WITH_FULL_BEAM((byte) 0x02),
        DRL((byte) 0x03),
        AUTOMATIC((byte) 0x04);
    
        public static FrontExteriorLight fromByte(byte byteValue) throws CommandParseException {
            FrontExteriorLight[] values = FrontExteriorLight.values();
    
            for (int i = 0; i < values.length; i++) {
                FrontExteriorLight state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("Enum FrontExteriorLight does not contain " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        FrontExteriorLight(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }

    public enum SwitchPosition implements ByteEnum {
        AUTOMATIC((byte) 0x00),
        DIPPED_HEADLIGHTS((byte) 0x01),
        PARKING_LIGHT_RIGHT((byte) 0x02),
        PARKING_LIGHT_LEFT((byte) 0x03),
        SIDELIGHTS((byte) 0x04);
    
        public static SwitchPosition fromByte(byte byteValue) throws CommandParseException {
            SwitchPosition[] values = SwitchPosition.values();
    
            for (int i = 0; i < values.length; i++) {
                SwitchPosition state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("Enum SwitchPosition does not contain " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        SwitchPosition(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}