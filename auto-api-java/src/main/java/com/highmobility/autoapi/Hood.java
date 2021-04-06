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
import com.highmobility.autoapi.value.LockSafety;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.value.Bytes;

import static com.highmobility.autoapi.property.ByteEnum.enumValueDoesNotExist;

/**
 * The Hood capability
 */
public class Hood {
    public static final int IDENTIFIER = Identifier.HOOD;

    public static final byte PROPERTY_POSITION = 0x01;
    public static final byte PROPERTY_LOCK = 0x02;
    public static final byte PROPERTY_LOCK_SAFETY = 0x03;

    /**
     * Get Hood property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Hood property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Hood property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Hood property availabilities
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
     * Get Hood properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Hood properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Hood properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Hood properties
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
     * Get specific Hood properties
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
     * The hood state
     */
    public static class State extends SetCommand {
        Property<Position> position = new Property<>(Position.class, PROPERTY_POSITION);
        Property<LockState> lock = new Property<>(LockState.class, PROPERTY_LOCK);
        Property<LockSafety> lockSafety = new Property<>(LockSafety.class, PROPERTY_LOCK_SAFETY);
    
        /**
         * @return The position
         */
        public Property<Position> getPosition() {
            return position;
        }
    
        /**
         * @return Includes the lock state of the hood.
         */
        public Property<LockState> getLock() {
            return lock;
        }
    
        /**
         * @return Indicates the safe-state of the hood.
         */
        public Property<LockSafety> getLockSafety() {
            return lockSafety;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_POSITION: return position.update(p);
                        case PROPERTY_LOCK: return lock.update(p);
                        case PROPERTY_LOCK_SAFETY: return lockSafety.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            position = builder.position;
            lock = builder.lock;
            lockSafety = builder.lockSafety;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<Position> position;
            private Property<LockState> lock;
            private Property<LockSafety> lockSafety;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param position The position
             * @return The builder
             */
            public Builder setPosition(Property<Position> position) {
                this.position = position.setIdentifier(PROPERTY_POSITION);
                addProperty(this.position);
                return this;
            }
            
            /**
             * @param lock Includes the lock state of the hood.
             * @return The builder
             */
            public Builder setLock(Property<LockState> lock) {
                this.lock = lock.setIdentifier(PROPERTY_LOCK);
                addProperty(this.lock);
                return this;
            }
            
            /**
             * @param lockSafety Indicates the safe-state of the hood.
             * @return The builder
             */
            public Builder setLockSafety(Property<LockSafety> lockSafety) {
                this.lockSafety = lockSafety.setIdentifier(PROPERTY_LOCK_SAFETY);
                addProperty(this.lockSafety);
                return this;
            }
        }
    }

    public enum Position implements ByteEnum {
        CLOSED((byte) 0x00),
        OPEN((byte) 0x01),
        INTERMEDIATE((byte) 0x02);
    
        public static Position fromByte(byte byteValue) throws CommandParseException {
            Position[] values = Position.values();
    
            for (int i = 0; i < values.length; i++) {
                Position state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException(
                enumValueDoesNotExist(Position.class.getSimpleName(), byteValue)
            );
        }
    
        private final byte value;
    
        Position(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}