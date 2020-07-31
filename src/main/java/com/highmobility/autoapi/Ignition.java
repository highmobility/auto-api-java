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

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.OnOffState;
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
     * Get all ignition properties
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
     * Get specific ignition properties
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
     * The ignition state
     */
    public static class State extends SetCommand {
        Property<OnOffState> status = new Property(OnOffState.class, PROPERTY_STATUS);
        Property<OnOffState> accessoriesStatus = new Property(OnOffState.class, PROPERTY_ACCESSORIES_STATUS);
        Property<IgnitionState> state = new Property(IgnitionState.class, PROPERTY_STATE);
    
        /**
         * @return The status
         */
        public Property<OnOffState> getStatus() {
            return status;
        }
    
        /**
         * @return The accessories status
         */
        public Property<OnOffState> getAccessoriesStatus() {
            return accessoriesStatus;
        }
    
        /**
         * @return The state
         */
        public Property<IgnitionState> getState() {
            return state;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                        case PROPERTY_ACCESSORIES_STATUS: return accessoriesStatus.update(p);
                        case PROPERTY_STATE: return state.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            status = builder.status;
            accessoriesStatus = builder.accessoriesStatus;
            state = builder.state;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<OnOffState> status;
            private Property<OnOffState> accessoriesStatus;
            private Property<IgnitionState> state;
    
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
             * @param accessoriesStatus The accessories status
             * @return The builder
             */
            public Builder setAccessoriesStatus(Property<OnOffState> accessoriesStatus) {
                this.accessoriesStatus = accessoriesStatus.setIdentifier(PROPERTY_ACCESSORIES_STATUS);
                addProperty(this.accessoriesStatus);
                return this;
            }
            
            /**
             * @param state The state
             * @return The builder
             */
            public Builder setState(Property<IgnitionState> state) {
                this.state = state.setIdentifier(PROPERTY_STATE);
                addProperty(this.state);
                return this;
            }
        }
    }

    /**
     * Turn ignition on off
     */
    public static class TurnIgnitionOnOff extends SetCommand {
        Property<OnOffState> status = new Property(OnOffState.class, PROPERTY_STATUS);
    
        /**
         * @return The status
         */
        public Property<OnOffState> getStatus() {
            return status;
        }
        
        /**
         * Turn ignition on off
         *
         * @param status The status
         */
        public TurnIgnitionOnOff(OnOffState status) {
            super(IDENTIFIER);
        
            addProperty(this.status.update(status));
            createBytes();
        }
    
        TurnIgnitionOnOff(byte[] bytes) throws CommandParseException, NoPropertiesException {
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

    public enum IgnitionState implements ByteEnum {
        LOCK((byte) 0x00),
        OFF((byte) 0x01),
        ACCESSORY((byte) 0x02),
        ON((byte) 0x03),
        START((byte) 0x04);
    
        public static IgnitionState fromByte(byte byteValue) throws CommandParseException {
            IgnitionState[] values = IgnitionState.values();
    
            for (int i = 0; i < values.length; i++) {
                IgnitionState state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        IgnitionState(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}