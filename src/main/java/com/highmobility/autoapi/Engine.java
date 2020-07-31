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
import com.highmobility.autoapi.value.OnOffState;
import com.highmobility.value.Bytes;

/**
 * The Engine capability
 */
public class Engine {
    public static final int IDENTIFIER = Identifier.ENGINE;

    public static final byte PROPERTY_STATUS = 0x01;
    public static final byte PROPERTY_START_STOP_STATE = 0x02;

    /**
     * Get all engine properties
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
     * Get specific engine properties
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
     * The engine state
     */
    public static class State extends SetCommand {
        Property<OnOffState> status = new Property(OnOffState.class, PROPERTY_STATUS);
        Property<ActiveState> startStopState = new Property(ActiveState.class, PROPERTY_START_STOP_STATE);
    
        /**
         * @return The status
         */
        public Property<OnOffState> getStatus() {
            return status;
        }
    
        /**
         * @return The start stop state
         */
        public Property<ActiveState> getStartStopState() {
            return startStopState;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                        case PROPERTY_START_STOP_STATE: return startStopState.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            status = builder.status;
            startStopState = builder.startStopState;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<OnOffState> status;
            private Property<ActiveState> startStopState;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param status The status
             * @return The builder
             */
            public Builder setStatus(Property<OnOffState> status) {
                this.status = status.setIdentifier(PROPERTY_STATUS);
                addProperty(this.status);
                return this;
            }
            
            /**
             * @param startStopState The start stop state
             * @return The builder
             */
            public Builder setStartStopState(Property<ActiveState> startStopState) {
                this.startStopState = startStopState.setIdentifier(PROPERTY_START_STOP_STATE);
                addProperty(this.startStopState);
                return this;
            }
        }
    }

    /**
     * Turn engine on off
     */
    public static class TurnEngineOnOff extends SetCommand {
        Property<OnOffState> status = new Property(OnOffState.class, PROPERTY_STATUS);
    
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
    
        TurnEngineOnOff(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                    }
                    return null;
                });
            }
            if (this.status.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Activate deactivate start stop
     */
    public static class ActivateDeactivateStartStop extends SetCommand {
        Property<ActiveState> startStopState = new Property(ActiveState.class, PROPERTY_START_STOP_STATE);
    
        /**
         * @return The start stop state
         */
        public Property<ActiveState> getStartStopState() {
            return startStopState;
        }
        
        /**
         * Activate deactivate start stop
         *
         * @param startStopState The start stop state
         */
        public ActivateDeactivateStartStop(ActiveState startStopState) {
            super(IDENTIFIER);
        
            addProperty(this.startStopState.update(startStopState));
            createBytes();
        }
    
        ActivateDeactivateStartStop(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_START_STOP_STATE: return startStopState.update(p);
                    }
                    return null;
                });
            }
            if (this.startStopState.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
}