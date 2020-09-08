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
import com.highmobility.value.Bytes;

/**
 * The Parking Brake capability
 */
public class ParkingBrake {
    public static final int IDENTIFIER = Identifier.PARKING_BRAKE;

    public static final byte PROPERTY_STATUS = 0x01;

    /**
     * Get all parking brake properties
     */
    public static class GetState extends GetCommand<State> {
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        GetState(byte[] bytes) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * Set parking brake
     */
    public static class SetParkingBrake extends SetCommand {
        Property<ActiveState> status = new Property<>(ActiveState.class, PROPERTY_STATUS);
    
        /**
         * @return The status
         */
        public Property<ActiveState> getStatus() {
            return status;
        }
        
        /**
         * Set parking brake
         *
         * @param status The status
         */
        public SetParkingBrake(ActiveState status) {
            super(IDENTIFIER);
        
            addProperty(this.status.update(status));
            createBytes();
        }
    
        SetParkingBrake(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_STATUS) return status.update(p);
                    return null;
                });
            }
            if (this.status.getValue() == null) 
                throw new NoPropertiesException();
        }
    }

    /**
     * The parking brake state
     */
    public static class State extends SetCommand {
        Property<ActiveState> status = new Property<>(ActiveState.class, PROPERTY_STATUS);
    
        /**
         * @return The status
         */
        public Property<ActiveState> getStatus() {
            return status;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            status = builder.status;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<ActiveState> status;
    
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
            public Builder setStatus(Property<ActiveState> status) {
                this.status = status.setIdentifier(PROPERTY_STATUS);
                addProperty(this.status);
                return this;
            }
        }
    }

    /**
     * Get all parking brake property availabilities
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
     * Get specific parking brake property availabilities.
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