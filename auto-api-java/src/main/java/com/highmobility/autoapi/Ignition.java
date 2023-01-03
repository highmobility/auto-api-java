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
import com.highmobility.autoapi.value.IgnitionState;
import com.highmobility.value.Bytes;

/**
 * The Ignition capability
 */
public class Ignition {
    public static final int IDENTIFIER = Identifier.IGNITION;

    public static final byte PROPERTY_STATUS = 0x01;
    public static final byte PROPERTY_ACCESSORIES_STATUS = 0x02;
    public static final byte PROPERTY_STATE = 0x03;

    /**
     * Get Ignition property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Ignition property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Ignition property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Ignition property availabilities
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
     * Get Ignition properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Ignition properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Ignition properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Ignition properties
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
     * Get specific Ignition properties
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
     * Turn ignition on off
     */
    public static class TurnIgnitionOnOff extends SetCommand {
        Property<IgnitionState> state = new Property<>(IgnitionState.class, PROPERTY_STATE);
    
        /**
         * @return The state
         */
        public Property<IgnitionState> getState() {
            return state;
        }
        
        /**
         * Turn ignition on off
         * 
         * @param state The state
         */
        public TurnIgnitionOnOff(IgnitionState state) {
            super(IDENTIFIER);
        
            addProperty(this.state.update(state));
            createBytes();
        }
    
        TurnIgnitionOnOff(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_STATE) return state.update(p);
                    
                    return null;
                });
            }
            if (this.state.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The ignition state
     */
    public static class State extends SetCommand {
        Property<IgnitionState> status = new Property<>(IgnitionState.class, PROPERTY_STATUS);
        Property<IgnitionState> accessoriesStatus = new Property<>(IgnitionState.class, PROPERTY_ACCESSORIES_STATUS);
        Property<IgnitionState> state = new Property<>(IgnitionState.class, PROPERTY_STATE);
    
        /**
         * @return The status
         * @deprecated combined with 'accessories_status'. Replaced by {@link #getState()}
         */
        @Deprecated
        public Property<IgnitionState> getStatus() {
            return status;
        }
    
        /**
         * @return The accessories status
         * @deprecated combined with 'status'. Replaced by {@link #getState()}
         */
        @Deprecated
        public Property<IgnitionState> getAccessoriesStatus() {
            return accessoriesStatus;
        }
    
        /**
         * @return The state
         */
        public Property<IgnitionState> getState() {
            return state;
        }
    
        State(byte[] bytes) {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                        case PROPERTY_ACCESSORIES_STATUS: return accessoriesStatus.update(p);
                        case PROPERTY_STATE: return state.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        public static final class Builder extends SetCommand.Builder<Builder> {
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                SetCommand baseSetCommand = super.build();
                Command resolved = CommandResolver.resolve(baseSetCommand.getByteArray());
                return (State) resolved;
            }
    
            /**
             * @param status The status
             * @return The builder
             * @deprecated combined with 'accessories_status'. Replaced by {@link #getState()}
             */
            @Deprecated
            public Builder setStatus(Property<IgnitionState> status) {
                Property property = status.setIdentifier(PROPERTY_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param accessoriesStatus The accessories status
             * @return The builder
             * @deprecated combined with 'status'. Replaced by {@link #getState()}
             */
            @Deprecated
            public Builder setAccessoriesStatus(Property<IgnitionState> accessoriesStatus) {
                Property property = accessoriesStatus.setIdentifier(PROPERTY_ACCESSORIES_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param state The state
             * @return The builder
             */
            public Builder setState(Property<IgnitionState> state) {
                Property property = state.setIdentifier(PROPERTY_STATE);
                addProperty(property);
                return this;
            }
        }
    }
}