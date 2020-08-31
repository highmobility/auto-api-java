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

import javax.annotation.Nullable;
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Lock;
import com.highmobility.autoapi.value.DoorPosition;
import com.highmobility.autoapi.value.LockState;
import java.util.ArrayList;
import java.util.List;
import com.highmobility.value.Bytes;

/**
 * The Doors capability
 */
public class Doors {
    public static final int IDENTIFIER = Identifier.DOORS;

    public static final byte PROPERTY_INSIDE_LOCKS = 0x02;
    public static final byte PROPERTY_LOCKS = 0x03;
    public static final byte PROPERTY_POSITIONS = 0x04;
    public static final byte PROPERTY_INSIDE_LOCKS_STATE = 0x05;
    public static final byte PROPERTY_LOCKS_STATE = 0x06;

    /**
     * Get all doors properties
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
     * Get specific doors properties
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
     * The doors state
     */
    public static class State extends SetCommand {
        List<Property<Lock>> insideLocks;
        List<Property<Lock>> locks;
        List<Property<DoorPosition>> positions;
        Property<LockState> insideLocksState = new Property<>(LockState.class, PROPERTY_INSIDE_LOCKS_STATE);
        Property<LockState> locksState = new Property<>(LockState.class, PROPERTY_LOCKS_STATE);
    
        /**
         * @return Inside lock states for the given doors
         */
        public List<Property<Lock>> getInsideLocks() {
            return insideLocks;
        }
    
        /**
         * @return Lock states for the given doors
         */
        public List<Property<Lock>> getLocks() {
            return locks;
        }
    
        /**
         * @return Door positions for the given doors
         */
        public List<Property<DoorPosition>> getPositions() {
            return positions;
        }
    
        /**
         * @return Inside locks state for the whole vehicle (combines all specific lock states if available)
         */
        public Property<LockState> getInsideLocksState() {
            return insideLocksState;
        }
    
        /**
         * @return Locks state for the whole vehicle (combines all specific lock states if available)
         */
        public Property<LockState> getLocksState() {
            return locksState;
        }
    
        /**
         * Get the outside lock state for a specific door.
         *
         * @param doorLocation The door doorLocation.
         * @return The outside lock state.
         */
        @Nullable public Property<Lock> getLock(Location doorLocation) {
            for (Property<Lock> outsideLockState : locks) {
                if (outsideLockState.getValue() != null && outsideLockState.getValue().getLocation() == doorLocation)
                    return outsideLockState;
            }
            return null;
        }
    
        /**
         * Get the inside lock state for a specific door.
         *
         * @param doorLocation The door doorLocation.
         * @return The inside lock state.
         */
        @Nullable public Property<Lock> getInsideLock(Location doorLocation) {
            for (Property<Lock> insideLockState : insideLocks) {
                if (insideLockState.getValue() != null && insideLockState.getValue().getLocation() == doorLocation)
                    return insideLockState;
            }
    
            return null;
        }
    
        /**
         * Get the outside lock state for a specific door.
         *
         * @param doorLocation The door doorLocation.
         * @return The outside lock state.
         */
        @Nullable public Property<DoorPosition> getPosition(DoorPosition.Location doorLocation) {
            for (Property<DoorPosition> position : positions) {
                if (position.getValue() != null && position.getValue().getLocation() == doorLocation)
                    return position;
            }
            return null;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
    
            final ArrayList<Property<Lock>> insideLocksBuilder = new ArrayList<>();
            final ArrayList<Property<Lock>> locksBuilder = new ArrayList<>();
            final ArrayList<Property<DoorPosition>> positionsBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_INSIDE_LOCKS:
                            Property<Lock> insideLock = new Property<>(Lock.class, p);
                            insideLocksBuilder.add(insideLock);
                            return insideLock;
                        case PROPERTY_LOCKS:
                            Property<Lock> lock = new Property<>(Lock.class, p);
                            locksBuilder.add(lock);
                            return lock;
                        case PROPERTY_POSITIONS:
                            Property<DoorPosition> position = new Property<>(DoorPosition.class, p);
                            positionsBuilder.add(position);
                            return position;
                        case PROPERTY_INSIDE_LOCKS_STATE: return insideLocksState.update(p);
                        case PROPERTY_LOCKS_STATE: return locksState.update(p);
                    }
    
                    return null;
                });
            }
    
            insideLocks = insideLocksBuilder;
            locks = locksBuilder;
            positions = positionsBuilder;
        }
    
        private State(Builder builder) {
            super(builder);
    
            insideLocks = builder.insideLocks;
            locks = builder.locks;
            positions = builder.positions;
            insideLocksState = builder.insideLocksState;
            locksState = builder.locksState;
        }
    
        public static final class Builder extends SetCommand.Builder {
            private final List<Property<Lock>> insideLocks = new ArrayList<>();
            private final List<Property<Lock>> locks = new ArrayList<>();
            private final List<Property<DoorPosition>> positions = new ArrayList<>();
            private Property<LockState> insideLocksState;
            private Property<LockState> locksState;
    
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                return new State(this);
            }
    
            /**
             * Add an array of inside locks.
             * 
             * @param insideLocks The inside locks. Inside lock states for the given doors
             * @return The builder
             */
            public Builder setInsideLocks(Property<Lock>[] insideLocks) {
                this.insideLocks.clear();
                for (int i = 0; i < insideLocks.length; i++) {
                    addInsideLock(insideLocks[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single inside lock.
             * 
             * @param insideLock The inside lock. Inside lock states for the given doors
             * @return The builder
             */
            public Builder addInsideLock(Property<Lock> insideLock) {
                insideLock.setIdentifier(PROPERTY_INSIDE_LOCKS);
                addProperty(insideLock);
                insideLocks.add(insideLock);
                return this;
            }
            
            /**
             * Add an array of locks.
             * 
             * @param locks The locks. Lock states for the given doors
             * @return The builder
             */
            public Builder setLocks(Property<Lock>[] locks) {
                this.locks.clear();
                for (int i = 0; i < locks.length; i++) {
                    addLock(locks[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single lock.
             * 
             * @param lock The lock. Lock states for the given doors
             * @return The builder
             */
            public Builder addLock(Property<Lock> lock) {
                lock.setIdentifier(PROPERTY_LOCKS);
                addProperty(lock);
                locks.add(lock);
                return this;
            }
            
            /**
             * Add an array of positions.
             * 
             * @param positions The positions. Door positions for the given doors
             * @return The builder
             */
            public Builder setPositions(Property<DoorPosition>[] positions) {
                this.positions.clear();
                for (int i = 0; i < positions.length; i++) {
                    addPosition(positions[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single position.
             * 
             * @param position The position. Door positions for the given doors
             * @return The builder
             */
            public Builder addPosition(Property<DoorPosition> position) {
                position.setIdentifier(PROPERTY_POSITIONS);
                addProperty(position);
                positions.add(position);
                return this;
            }
            
            /**
             * @param insideLocksState Inside locks state for the whole vehicle (combines all specific lock states if available)
             * @return The builder
             */
            public Builder setInsideLocksState(Property<LockState> insideLocksState) {
                this.insideLocksState = insideLocksState.setIdentifier(PROPERTY_INSIDE_LOCKS_STATE);
                addProperty(this.insideLocksState);
                return this;
            }
            
            /**
             * @param locksState Locks state for the whole vehicle (combines all specific lock states if available)
             * @return The builder
             */
            public Builder setLocksState(Property<LockState> locksState) {
                this.locksState = locksState.setIdentifier(PROPERTY_LOCKS_STATE);
                addProperty(this.locksState);
                return this;
            }
        }
    }

    /**
     * Lock unlock doors
     */
    public static class LockUnlockDoors extends SetCommand {
        Property<LockState> locksState = new Property<>(LockState.class, PROPERTY_LOCKS_STATE);
    
        /**
         * @return The locks state
         */
        public Property<LockState> getLocksState() {
            return locksState;
        }
        
        /**
         * Lock unlock doors
         *
         * @param locksState Locks state for the whole vehicle (combines all specific lock states if available)
         */
        public LockUnlockDoors(LockState locksState) {
            super(IDENTIFIER);
        
            addProperty(this.locksState.update(locksState));
            createBytes();
        }
    
        LockUnlockDoors(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_LOCKS_STATE) return locksState.update(p);
                    return null;
                });
            }
            if (this.locksState.getValue() == null) 
                throw new NoPropertiesException();
        }
    }
}