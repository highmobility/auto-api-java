/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Lock;
import com.highmobility.autoapi.value.DoorPosition;
import com.highmobility.autoapi.value.LockState;
import javax.annotation.Nullable;
import com.highmobility.autoapi.value.Location;
import java.util.ArrayList;
import java.util.List;

/**
 * The doors state
 */
public class DoorsState extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.DOORS;

    public static final byte IDENTIFIER_INSIDE_LOCKS = 0x02;
    public static final byte IDENTIFIER_LOCKS = 0x03;
    public static final byte IDENTIFIER_POSITIONS = 0x04;
    public static final byte IDENTIFIER_INSIDE_LOCKS_STATE = 0x05;
    public static final byte IDENTIFIER_LOCKS_STATE = 0x06;

    Property<Lock>[] insideLocks;
    Property<Lock>[] locks;
    Property<DoorPosition>[] positions;
    Property<LockState> insideLocksState = new Property(LockState.class, IDENTIFIER_INSIDE_LOCKS_STATE);
    Property<LockState> locksState = new Property(LockState.class, IDENTIFIER_LOCKS_STATE);

    /**
     * @return Inside lock states for the given doors
     */
    public Property<Lock>[] getInsideLocks() {
        return insideLocks;
    }

    /**
     * @return Lock states for the given doors
     */
    public Property<Lock>[] getLocks() {
        return locks;
    }

    /**
     * @return Door positions for the given doors
     */
    public Property<DoorPosition>[] getPositions() {
        return positions;
    }

    /**
     * @return Inside locks state for the whole car (combines all specific lock states if available)
     */
    public Property<LockState> getInsideLocksState() {
        return insideLocksState;
    }

    /**
     * @return Locks state for the whole car (combines all specific lock states if available)
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

    DoorsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> insideLocksBuilder = new ArrayList<>();
        ArrayList<Property> locksBuilder = new ArrayList<>();
        ArrayList<Property> positionsBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_INSIDE_LOCKS:
                        Property<Lock> insideLock = new Property(Lock.class, p);
                        insideLocksBuilder.add(insideLock);
                        return insideLock;
                    case IDENTIFIER_LOCKS:
                        Property<Lock> lock = new Property(Lock.class, p);
                        locksBuilder.add(lock);
                        return lock;
                    case IDENTIFIER_POSITIONS:
                        Property<DoorPosition> position = new Property(DoorPosition.class, p);
                        positionsBuilder.add(position);
                        return position;
                    case IDENTIFIER_INSIDE_LOCKS_STATE: return insideLocksState.update(p);
                    case IDENTIFIER_LOCKS_STATE: return locksState.update(p);
                }

                return null;
            });
        }

        insideLocks = insideLocksBuilder.toArray(new Property[0]);
        locks = locksBuilder.toArray(new Property[0]);
        positions = positionsBuilder.toArray(new Property[0]);
    }

    private DoorsState(Builder builder) {
        super(builder);

        insideLocks = builder.insideLocks.toArray(new Property[0]);
        locks = builder.locks.toArray(new Property[0]);
        positions = builder.positions.toArray(new Property[0]);
        insideLocksState = builder.insideLocksState;
        locksState = builder.locksState;
    }

    public static final class Builder extends SetCommand.Builder {
        private List<Property> insideLocks = new ArrayList<>();
        private List<Property> locks = new ArrayList<>();
        private List<Property> positions = new ArrayList<>();
        private Property<LockState> insideLocksState;
        private Property<LockState> locksState;

        public Builder() {
            super(IDENTIFIER);
        }

        public DoorsState build() {
            return new DoorsState(this);
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
            insideLock.setIdentifier(IDENTIFIER_INSIDE_LOCKS);
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
            lock.setIdentifier(IDENTIFIER_LOCKS);
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
            position.setIdentifier(IDENTIFIER_POSITIONS);
            addProperty(position);
            positions.add(position);
            return this;
        }
        
        /**
         * @param insideLocksState Inside locks state for the whole car (combines all specific lock states if available)
         * @return The builder
         */
        public Builder setInsideLocksState(Property<LockState> insideLocksState) {
            this.insideLocksState = insideLocksState.setIdentifier(IDENTIFIER_INSIDE_LOCKS_STATE);
            addProperty(this.insideLocksState);
            return this;
        }
        
        /**
         * @param locksState Locks state for the whole car (combines all specific lock states if available)
         * @return The builder
         */
        public Builder setLocksState(Property<LockState> locksState) {
            this.locksState = locksState.setIdentifier(IDENTIFIER_LOCKS_STATE);
            addProperty(this.locksState);
            return this;
        }
    }
}