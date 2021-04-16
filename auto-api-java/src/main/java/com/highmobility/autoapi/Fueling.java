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
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.Position;
import com.highmobility.value.Bytes;
import javax.annotation.Nullable;

/**
 * The Fueling capability
 */
public class Fueling {
    public static final int IDENTIFIER = Identifier.FUELING;

    public static final byte PROPERTY_GAS_FLAP_LOCK = 0x02;
    public static final byte PROPERTY_GAS_FLAP_POSITION = 0x03;

    /**
     * Get Fueling property availability information
     */
    public static class GetGasFlapStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Fueling property availability
         */
        public GetGasFlapStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Fueling property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetGasFlapStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Fueling property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetGasFlapStateAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetGasFlapStateAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get gas flap state
     */
    public static class GetGasFlapState extends GetCommand<State> {
        /**
         * Get all Fueling properties
         */
        public GetGasFlapState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Fueling properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetGasFlapState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Fueling properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetGasFlapState(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetGasFlapState(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Fueling properties
     * 
     * @deprecated use {@link GetGasFlapState#GetGasFlapState(byte...)} instead
     */
    @Deprecated
    public static class GetGasFlapProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetGasFlapProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetGasFlapProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetGasFlapProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * Control gas flap
     */
    public static class ControlGasFlap extends SetCommand {
        Property<LockState> gasFlapLock = new Property<>(LockState.class, PROPERTY_GAS_FLAP_LOCK);
        Property<Position> gasFlapPosition = new Property<>(Position.class, PROPERTY_GAS_FLAP_POSITION);
    
        /**
         * @return The gas flap lock
         */
        public Property<LockState> getGasFlapLock() {
            return gasFlapLock;
        }
        
        /**
         * @return The gas flap position
         */
        public Property<Position> getGasFlapPosition() {
            return gasFlapPosition;
        }
        
        /**
         * Control gas flap
         * 
         * @param gasFlapLock The gas flap lock
         * @param gasFlapPosition The gas flap position
         */
        public ControlGasFlap(@Nullable LockState gasFlapLock, @Nullable Position gasFlapPosition) {
            super(IDENTIFIER);
        
            addProperty(this.gasFlapLock.update(gasFlapLock));
            addProperty(this.gasFlapPosition.update(gasFlapPosition));
            if (this.gasFlapLock.getValue() == null && this.gasFlapPosition.getValue() == null) throw new IllegalArgumentException();
            createBytes();
        }
    
        ControlGasFlap(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_GAS_FLAP_LOCK: return gasFlapLock.update(p);
                        case PROPERTY_GAS_FLAP_POSITION: return gasFlapPosition.update(p);
                    }
        
                    return null;
                });
            }
            if (this.gasFlapLock.getValue() == null && this.gasFlapPosition.getValue() == null) {
                throw new PropertyParseException(optionalPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The fueling state
     */
    public static class State extends SetCommand {
        Property<LockState> gasFlapLock = new Property<>(LockState.class, PROPERTY_GAS_FLAP_LOCK);
        Property<Position> gasFlapPosition = new Property<>(Position.class, PROPERTY_GAS_FLAP_POSITION);
    
        /**
         * @return The gas flap lock
         */
        public Property<LockState> getGasFlapLock() {
            return gasFlapLock;
        }
    
        /**
         * @return The gas flap position
         */
        public Property<Position> getGasFlapPosition() {
            return gasFlapPosition;
        }
    
        State(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_GAS_FLAP_LOCK: return gasFlapLock.update(p);
                        case PROPERTY_GAS_FLAP_POSITION: return gasFlapPosition.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            gasFlapLock = builder.gasFlapLock;
            gasFlapPosition = builder.gasFlapPosition;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<LockState> gasFlapLock;
            private Property<Position> gasFlapPosition;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param gasFlapLock The gas flap lock
             * @return The builder
             */
            public Builder setGasFlapLock(Property<LockState> gasFlapLock) {
                this.gasFlapLock = gasFlapLock.setIdentifier(PROPERTY_GAS_FLAP_LOCK);
                addProperty(this.gasFlapLock);
                return this;
            }
            
            /**
             * @param gasFlapPosition The gas flap position
             * @return The builder
             */
            public Builder setGasFlapPosition(Property<Position> gasFlapPosition) {
                this.gasFlapPosition = gasFlapPosition.setIdentifier(PROPERTY_GAS_FLAP_POSITION);
                addProperty(this.gasFlapPosition);
                return this;
            }
        }
    }
}