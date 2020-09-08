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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.measurement.Illuminance;
import com.highmobility.value.Bytes;

/**
 * The Light Conditions capability
 */
public class LightConditions {
    public static final int IDENTIFIER = Identifier.LIGHT_CONDITIONS;

    public static final byte PROPERTY_OUTSIDE_LIGHT = 0x01;
    public static final byte PROPERTY_INSIDE_LIGHT = 0x02;

    /**
     * Get light conditions
     */
    public static class GetLightConditions extends GetCommand<State> {
        public GetLightConditions() {
            super(State.class, IDENTIFIER);
        }
    
        GetLightConditions(byte[] bytes) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * Get specific light conditions properties
     */
    public static class GetLightConditionsProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetLightConditionsProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetLightConditionsProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetLightConditionsProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The light conditions state
     */
    public static class State extends SetCommand {
        Property<Illuminance> outsideLight = new Property<>(Illuminance.class, PROPERTY_OUTSIDE_LIGHT);
        Property<Illuminance> insideLight = new Property<>(Illuminance.class, PROPERTY_INSIDE_LIGHT);
    
        /**
         * @return Measured outside illuminance
         */
        public Property<Illuminance> getOutsideLight() {
            return outsideLight;
        }
    
        /**
         * @return Measured inside illuminance
         */
        public Property<Illuminance> getInsideLight() {
            return insideLight;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_OUTSIDE_LIGHT: return outsideLight.update(p);
                        case PROPERTY_INSIDE_LIGHT: return insideLight.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            outsideLight = builder.outsideLight;
            insideLight = builder.insideLight;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<Illuminance> outsideLight;
            private Property<Illuminance> insideLight;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param outsideLight Measured outside illuminance
             * @return The builder
             */
            public Builder setOutsideLight(Property<Illuminance> outsideLight) {
                this.outsideLight = outsideLight.setIdentifier(PROPERTY_OUTSIDE_LIGHT);
                addProperty(this.outsideLight);
                return this;
            }
            
            /**
             * @param insideLight Measured inside illuminance
             * @return The builder
             */
            public Builder setInsideLight(Property<Illuminance> insideLight) {
                this.insideLight = insideLight.setIdentifier(PROPERTY_INSIDE_LIGHT);
                addProperty(this.insideLight);
                return this;
            }
        }
    }

    /**
     * Get all light conditions property availabilities
     */
    public static class GetAllAvailabilities extends GetAvailabilityCommand {
        public GetAllAvailabilities() {
            super(IDENTIFIER);
        }
    
        GetAllAvailabilities(byte[] bytes) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get specific light conditions property availabilities.
     */
    public static class GetAvailabilities extends GetAvailabilityCommand {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetAvailabilities(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetAvailabilities(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetAvailabilities(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }
}