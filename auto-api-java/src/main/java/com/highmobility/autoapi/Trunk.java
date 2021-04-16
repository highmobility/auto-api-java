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
import com.highmobility.autoapi.value.LockSafety;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.Position;
import com.highmobility.value.Bytes;
import javax.annotation.Nullable;

/**
 * The Trunk capability
 */
public class Trunk {
    public static final int IDENTIFIER = Identifier.TRUNK;

    public static final byte PROPERTY_LOCK = 0x01;
    public static final byte PROPERTY_POSITION = 0x02;
    public static final byte PROPERTY_LOCK_SAFETY = 0x03;

    /**
     * Get Trunk property availability information
     */
    public static class GetStateAvailability extends GetAvailabilityCommand {
        /**
         * Get every Trunk property availability
         */
        public GetStateAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Trunk property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetStateAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Trunk property availabilities
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
     * Get Trunk properties
     */
    public static class GetState extends GetCommand<State> {
        /**
         * Get all Trunk properties
         */
        public GetState() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Trunk properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetState(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Trunk properties
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
     * Get specific Trunk properties
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
     * Control trunk
     */
    public static class ControlTrunk extends SetCommand {
        Property<LockState> lock = new Property<>(LockState.class, PROPERTY_LOCK);
        Property<Position> position = new Property<>(Position.class, PROPERTY_POSITION);
    
        /**
         * @return The lock
         */
        public Property<LockState> getLock() {
            return lock;
        }
        
        /**
         * @return The position
         */
        public Property<Position> getPosition() {
            return position;
        }
        
        /**
         * Control trunk
         * 
         * @param lock The lock
         * @param position The position
         */
        public ControlTrunk(@Nullable LockState lock, @Nullable Position position) {
            super(IDENTIFIER);
        
            addProperty(this.lock.update(lock));
            addProperty(this.position.update(position));
            if (this.lock.getValue() == null && this.position.getValue() == null) throw new IllegalArgumentException();
            createBytes();
        }
    
        ControlTrunk(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_LOCK: return lock.update(p);
                        case PROPERTY_POSITION: return position.update(p);
                    }
        
                    return null;
                });
            }
            if (this.lock.getValue() == null && this.position.getValue() == null) {
                throw new PropertyParseException(optionalPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The trunk state
     */
    public static class State extends SetCommand {
        Property<LockState> lock = new Property<>(LockState.class, PROPERTY_LOCK);
        Property<Position> position = new Property<>(Position.class, PROPERTY_POSITION);
        Property<LockSafety> lockSafety = new Property<>(LockSafety.class, PROPERTY_LOCK_SAFETY);
    
        /**
         * @return The lock
         */
        public Property<LockState> getLock() {
            return lock;
        }
    
        /**
         * @return The position
         */
        public Property<Position> getPosition() {
            return position;
        }
    
        /**
         * @return Indicates the safe-state of the trunk.
         */
        public Property<LockSafety> getLockSafety() {
            return lockSafety;
        }
    
        State(byte[] bytes) throws CommandParseException, PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_LOCK: return lock.update(p);
                        case PROPERTY_POSITION: return position.update(p);
                        case PROPERTY_LOCK_SAFETY: return lockSafety.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        private State(Builder builder) {
            super(builder);
    
            lock = builder.lock;
            position = builder.position;
            lockSafety = builder.lockSafety;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private Property<LockState> lock;
            private Property<Position> position;
            private Property<LockSafety> lockSafety;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * @param lock The lock
             * @return The builder
             */
            public Builder setLock(Property<LockState> lock) {
                this.lock = lock.setIdentifier(PROPERTY_LOCK);
                addProperty(this.lock);
                return this;
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
             * @param lockSafety Indicates the safe-state of the trunk.
             * @return The builder
             */
            public Builder setLockSafety(Property<LockSafety> lockSafety) {
                this.lockSafety = lockSafety.setIdentifier(PROPERTY_LOCK_SAFETY);
                addProperty(this.lockSafety);
                return this;
            }
        }
    }
}