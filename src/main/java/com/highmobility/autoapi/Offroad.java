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
import com.highmobility.autoapi.value.measurement.Angle;
import com.highmobility.value.Bytes;

/**
 * The Offroad capability
 */
public class Offroad {
    public static final int IDENTIFIER = Identifier.OFFROAD;

    public static final byte PROPERTY_ROUTE_INCLINE = 0x01;
    public static final byte PROPERTY_WHEEL_SUSPENSION = 0x02;

    /**
     * Get Offroad property availability information.
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Offroad property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Offroad property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Offroad property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetStateAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get Offroad properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Offroad properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Offroad properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Offroad properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetState(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Offroad properties
     * 
     * @deprecated use {@link GetState#GetState(byte...)} instead
     */
    @Deprecated
    public static class GetProperties extends GetCommand<State> {
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
     * The offroad state
     */
    public static class State extends SetCommand {
        Property<Angle> routeIncline = new Property<>(Angle.class, PROPERTY_ROUTE_INCLINE);
        Property<Double> wheelSuspension = new Property<>(Double.class, PROPERTY_WHEEL_SUSPENSION);
    
        /**
         * @return The route elevation incline
         */
        public Property<Angle> getRouteIncline() {
            return routeIncline;
        }
    
        /**
         * @return The wheel suspension level percentage, whereas 0.0 is no suspension and 1.0 maximum suspension
         */
        public Property<Double> getWheelSuspension() {
            return wheelSuspension;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_ROUTE_INCLINE: return routeIncline.update(p);
                        case PROPERTY_WHEEL_SUSPENSION: return wheelSuspension.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            routeIncline = builder.routeIncline;
            wheelSuspension = builder.wheelSuspension;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<Angle> routeIncline;
            private Property<Double> wheelSuspension;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param routeIncline The route elevation incline
             * @return The builder
             */
            public Builder setRouteIncline(Property<Angle> routeIncline) {
                this.routeIncline = routeIncline.setIdentifier(PROPERTY_ROUTE_INCLINE);
                addProperty(this.routeIncline);
                return this;
            }
            
            /**
             * @param wheelSuspension The wheel suspension level percentage, whereas 0.0 is no suspension and 1.0 maximum suspension
             * @return The builder
             */
            public Builder setWheelSuspension(Property<Double> wheelSuspension) {
                this.wheelSuspension = wheelSuspension.setIdentifier(PROPERTY_WHEEL_SUSPENSION);
                addProperty(this.wheelSuspension);
                return this;
            }
        }
    }
}