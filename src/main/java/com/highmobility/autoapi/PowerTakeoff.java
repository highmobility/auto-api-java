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
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

import static com.highmobility.utils.ByteUtils.hexFromByte;

/**
 * The Power Take-Off capability
 */
public class PowerTakeoff {
    public static final int IDENTIFIER = Identifier.POWER_TAKEOFF;

    public static final byte PROPERTY_STATUS = 0x01;
    public static final byte PROPERTY_ENGAGED = 0x02;

    /**
     * Get all power takeoff properties
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
     * Get specific power takeoff properties
     */
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
     * The power takeoff state
     */
    public static class State extends SetCommand {
        Property<ActiveState> status = new Property<>(ActiveState.class, PROPERTY_STATUS);
        Property<Engaged> engaged = new Property<>(Engaged.class, PROPERTY_ENGAGED);
    
        /**
         * @return The status
         */
        public Property<ActiveState> getStatus() {
            return status;
        }
    
        /**
         * @return The engaged
         */
        public Property<Engaged> getEngaged() {
            return engaged;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                        case PROPERTY_ENGAGED: return engaged.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            status = builder.status;
            engaged = builder.engaged;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<ActiveState> status;
            private Property<Engaged> engaged;
    
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
            
            /**
             * @param engaged The engaged
             * @return The builder
             */
            public Builder setEngaged(Property<Engaged> engaged) {
                this.engaged = engaged.setIdentifier(PROPERTY_ENGAGED);
                addProperty(this.engaged);
                return this;
            }
        }
    }

    /**
     * Activate deactivate power takeoff
     */
    public static class ActivateDeactivatePowerTakeoff extends SetCommand {
        Property<ActiveState> status = new Property<>(ActiveState.class, PROPERTY_STATUS);
    
        /**
         * @return The status
         */
        public Property<ActiveState> getStatus() {
            return status;
        }
        
        /**
         * Activate deactivate power takeoff
         *
         * @param status The status
         */
        public ActivateDeactivatePowerTakeoff(ActiveState status) {
            super(IDENTIFIER);
        
            addProperty(this.status.update(status));
            createBytes();
        }
    
        ActivateDeactivatePowerTakeoff(byte[] bytes) throws CommandParseException, NoPropertiesException {
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

    public enum Engaged implements ByteEnum {
        NOT_ENGAGED((byte) 0x00),
        ENGAGED((byte) 0x01);
    
        public static Engaged fromByte(byte byteValue) throws CommandParseException {
            Engaged[] values = Engaged.values();
    
            for (int i = 0; i < values.length; i++) {
                Engaged state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException("PowerTakeoff.Engaged does not contain: " + hexFromByte(byteValue));
        }
    
        private final byte value;
    
        Engaged(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}