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
     * Get Light Conditions property availability information
     */
    public static class GetLightConditionsAvailability extends GetAvailabilityCommand {
        /**
         * Get every Light Conditions property availability
         */
        public GetLightConditionsAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Light Conditions property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetLightConditionsAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Light Conditions property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetLightConditionsAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetLightConditionsAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get light conditions
     */
    public static class GetLightConditions extends GetCommand<State> {
        /**
         * Get all Light Conditions properties
         */
        public GetLightConditions() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Light Conditions properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetLightConditions(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Light Conditions properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetLightConditions(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetLightConditions(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Light Conditions properties
     * 
     * @deprecated use {@link GetLightConditions#GetLightConditions(byte...)} instead
     */
    @Deprecated
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
    
        State(byte[] bytes) throws CommandParseException, PropertyParseException {
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
    
        public static final class Builder extends SetCommand.Builder<Builder> {
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
}