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
import com.highmobility.autoapi.value.doors.DoorLockState;
import com.highmobility.autoapi.value.doors.DoorPosition;
import com.highmobility.autoapi.value.Location;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import static com.highmobility.autoapi.value.Lock.UNLOCKED;

/**
 * Command sent from the car every time the lock state changes or when a Get Lock State is received.
 * The new status is included in the command payload and may be the result of user, device or car
 * triggered action.
 */
public class LockState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.DOOR_LOCKS, 0x01);

    private static final byte INSIDE_LOCK_IDENTIFIER = 0x02;
    private static final byte OUTSIDE_LOCK_IDENTIFIER = 0x03;
    private static final byte POSITION_IDENTIFIER = 0x04;

    Property<DoorLockState>[] insideLocks;
    Property<DoorLockState>[] outsideLocks;
    Property<DoorPosition>[] positions;

    /**
     * @return The outside door lock states.
     */
    public Property<DoorLockState>[] getOutsideLocks() {
        return outsideLocks;
    }

    /**
     * Get the outside lock state for a specific door.
     *
     * @param doorLocation The door doorLocation.
     * @return The outside lock state.
     */
    @Nullable public Property<DoorLockState> getOutsideLock(Location doorLocation) {
        for (Property<DoorLockState> outsideLockState : outsideLocks) {
            if (outsideLockState.getValue() != null && outsideLockState.getValue().getLocation() == doorLocation)
                return outsideLockState;
        }
        return null;
    }

    /**
     * @return The inside door lock states.
     */
    public Property<DoorLockState>[] getInsideLocks() {
        return insideLocks;
    }

    /**
     * Get the inside lock state for a specific door.
     *
     * @param doorLocation The door doorLocation.
     * @return The inside lock state.
     */
    @Nullable public Property<DoorLockState> getInsideLock(Location doorLocation) {
        for (Property<DoorLockState> insideLockState : insideLocks) {
            if (insideLockState.getValue() != null && insideLockState.getValue().getLocation() == doorLocation)
                return insideLockState;
        }

        return null;
    }

    /**
     * @return The outside door lock states.
     */
    public Property<DoorPosition>[] getPositions() {
        return positions;
    }

    /**
     * Get the outside lock state for a specific door.
     *
     * @param doorLocation The door doorLocation.
     * @return The outside lock state.
     */
    @Nullable public Property<DoorPosition> getPosition(Location doorLocation) {
        for (Property<DoorPosition> position : positions) {
            if (position.getValue() != null && position.getValue().getLocation() == doorLocation)
                return position;
        }
        return null;
    }

    /**
     * @return Whether all of the outside door locks are locked.
     */
    public boolean isLocked() {
        for (Property<DoorLockState> lock : outsideLocks) {
            if (lock.getValue() == null || lock.getValue().getLock() == UNLOCKED) {
                return false;
            }
        }

        return true;
    }

    LockState(byte[] bytes) {
        super(bytes);

        ArrayList<Property> insideLocksBuilder = new ArrayList<>();
        ArrayList<Property> outsideLocksBuilder = new ArrayList<>();
        ArrayList<Property> lockAndPositionStatesBuilder = new ArrayList<>();

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case POSITION_IDENTIFIER:
                        Property pos = new Property(DoorPosition.class, p);
                        lockAndPositionStatesBuilder.add(pos);
                        return pos;
                    case INSIDE_LOCK_IDENTIFIER:
                        Property id = new Property(DoorLockState.class, p);
                        insideLocksBuilder.add(id);
                        return id;
                    case OUTSIDE_LOCK_IDENTIFIER:
                        Property od = new Property(DoorLockState.class, p);
                        outsideLocksBuilder.add(od);
                        return od;
                }

                return null;
            });
        }

        positions = lockAndPositionStatesBuilder.toArray(new Property[0]);
        insideLocks = insideLocksBuilder.toArray(new Property[0]);
        outsideLocks = outsideLocksBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private LockState(Builder builder) {
        super(builder);

        positions = builder.positions.toArray(new Property[0]);
        insideLocks = builder.insideLocks.toArray(new Property[0]);
        outsideLocks = builder.locks.toArray(new Property[0]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private List<Property> positions = new ArrayList<>();
        private List<Property> insideLocks = new ArrayList<>();
        private List<Property> locks = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        /**
         * @param positions The position states for all doors.
         * @return The builder.
         */
        public Builder setPositions(Property<DoorPosition>[] positions) {
            this.positions.clear();
            for (Property position : positions) {
                addPosition(position);
            }
            return this;
        }

        /**
         * Add the position state for a door.
         *
         * @param position The lock and position state.
         * @return The builder.
         */
        public Builder addPosition(Property<DoorPosition> position) {
            this.positions.add(position);
            position.setIdentifier(POSITION_IDENTIFIER);
            addProperty(position);
            return this;
        }

        /**
         * @param insideLocks The inside lock states for all of the doors.
         * @return The builder.
         */
        public Builder setInsideLocks(Property<DoorLockState>[] insideLocks) {
            this.insideLocks.clear();
            for (Property insideLockState : insideLocks) {
                addInsideLock(insideLockState);
            }
            return this;
        }

        /**
         * Add the inside lock state for a door.
         *
         * @param insideLock The lock state.
         * @return The builder.
         */
        public Builder addInsideLock(Property<DoorLockState> insideLock) {
            this.insideLocks.add(insideLock);
            insideLock.setIdentifier(INSIDE_LOCK_IDENTIFIER);
            addProperty(insideLock);
            return this;
        }

        /**
         * @param locks The lock states for all of the doors.
         * @return The builder.
         */
        public Builder setOutsideLocks(Property<DoorLockState>[] locks) {
            this.locks.clear();
            for (Property lock : locks) {
                addOutsideLock(lock);
            }
            return this;
        }

        /**
         * Add the lock state for a door.
         *
         * @param lockState The lock state.
         * @return The builder.
         */
        public Builder addOutsideLock(Property<DoorLockState> lockState) {
            this.locks.add(lockState);
            lockState.setIdentifier(OUTSIDE_LOCK_IDENTIFIER);
            addProperty(lockState);
            return this;
        }

        public LockState build() {
            return new LockState(this);
        }
    }
}