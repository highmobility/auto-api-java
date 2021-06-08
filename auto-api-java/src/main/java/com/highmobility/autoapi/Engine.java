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
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.EnabledState;
import com.highmobility.autoapi.value.OnOffState;
import com.highmobility.value.Bytes;

/**
 * The Engine capability
 */
public class Engine {
    public static final int IDENTIFIER = Identifier.ENGINE;

    public static final byte PROPERTY_STATUS = 0x01;
    public static final byte PROPERTY_START_STOP_STATE = 0x02;
    public static final byte PROPERTY_START_STOP_ENABLED = 0x03;

    /**
     * Get Engine property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Engine property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Engine property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Engine property availabilities
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
     * Get Engine properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Engine properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Engine properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Engine properties
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
     * Get specific Engine properties
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
     * Turn engine on off
     */
    public static class TurnEngineOnOff extends SetCommand {
        Property<OnOffState> status = new Property<>(OnOffState.class, PROPERTY_STATUS);
    
        /**
         * @return The status
         */
        public Property<OnOffState> getStatus() {
            return status;
        }
        
        /**
         * Turn engine on off
         * 
         * @param status The status
         */
        public TurnEngineOnOff(OnOffState status) {
            super(IDENTIFIER);
        
            addProperty(this.status.update(status));
            createBytes();
        }
    
        TurnEngineOnOff(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_STATUS) return status.update(p);
                    
                    return null;
                });
            }
            if (this.status.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * Enable disable start stop
     */
    public static class EnableDisableStartStop extends SetCommand {
        Property<EnabledState> startStopEnabled = new Property<>(EnabledState.class, PROPERTY_START_STOP_ENABLED);
    
        /**
         * @return The start stop enabled
         */
        public Property<EnabledState> getStartStopEnabled() {
            return startStopEnabled;
        }
        
        /**
         * Enable disable start stop
         * 
         * @param startStopEnabled Indicates if the automatic start-stop system is enabled or not
         */
        public EnableDisableStartStop(EnabledState startStopEnabled) {
            super(IDENTIFIER);
        
            addProperty(this.startStopEnabled.update(startStopEnabled));
            createBytes();
        }
    
        EnableDisableStartStop(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_START_STOP_ENABLED) return startStopEnabled.update(p);
                    
                    return null;
                });
            }
            if (this.startStopEnabled.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The engine state
     */
    public static class State extends SetCommand {
        Property<OnOffState> status = new Property<>(OnOffState.class, PROPERTY_STATUS);
        Property<ActiveState> startStopState = new Property<>(ActiveState.class, PROPERTY_START_STOP_STATE);
        Property<EnabledState> startStopEnabled = new Property<>(EnabledState.class, PROPERTY_START_STOP_ENABLED);
    
        /**
         * @return The status
         */
        public Property<OnOffState> getStatus() {
            return status;
        }
    
        /**
         * @return Indicates wheter the start-stop system is currently active or not
         */
        public Property<ActiveState> getStartStopState() {
            return startStopState;
        }
    
        /**
         * @return Indicates if the automatic start-stop system is enabled or not
         */
        public Property<EnabledState> getStartStopEnabled() {
            return startStopEnabled;
        }
    
        State(byte[] bytes) {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                        case PROPERTY_START_STOP_STATE: return startStopState.update(p);
                        case PROPERTY_START_STOP_ENABLED: return startStopEnabled.update(p);
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
             */
            public Builder setStatus(Property<OnOffState> status) {
                Property property = status.setIdentifier(PROPERTY_STATUS);
                addProperty(property);
                return this;
            }
            
            /**
             * @param startStopState Indicates wheter the start-stop system is currently active or not
             * @return The builder
             */
            public Builder setStartStopState(Property<ActiveState> startStopState) {
                Property property = startStopState.setIdentifier(PROPERTY_START_STOP_STATE);
                addProperty(property);
                return this;
            }
            
            /**
             * @param startStopEnabled Indicates if the automatic start-stop system is enabled or not
             * @return The builder
             */
            public Builder setStartStopEnabled(Property<EnabledState> startStopEnabled) {
                Property property = startStopEnabled.setIdentifier(PROPERTY_START_STOP_ENABLED);
                addProperty(property);
                return this;
            }
        }
    }
}