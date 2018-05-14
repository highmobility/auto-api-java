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

import com.highmobility.autoapi.property.DoorLockProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.doors.DoorLocation;
import com.highmobility.autoapi.property.doors.DoorLockAndPositionState;
import com.highmobility.autoapi.property.doors.DoorLockState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.highmobility.autoapi.property.doors.DoorLock.LOCKED;

/**
 * Command sent from the car every time the lock state changes or when a Get Lock State is received.
 * The new status is included in the command payload and may be the result of user, device or car
 * triggered action.
 */
public class LockState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.DOOR_LOCKS, 0x01);

    private static final byte INSIDE_LOCK_IDENTIFIER = 0x02;
    private static final byte OUTSIDE_LOCK_IDENTIFIER = 0x03;

    DoorLockProperty[] lockStates;

    DoorLockAndPositionState[] doorLockAndPositionStates;
    DoorLockState[] insideDoorLockStates;
    DoorLockState[] outsideDoorLockStates;

    /**
     * @return The lock and position states for all of the doors.
     * @deprecated use {@link #getDoorLockAndPositionStates()} instead.
     */
    @Deprecated
    public DoorLockProperty[] getDoorLockStates() {
        return lockStates;
    }

    /**
     * Get the lock and position state for a specific door.
     *
     * @param location The location of the door.
     * @return The lock and position state.
     * @deprecated use {@link #getDoorLockAndPositionState(DoorLocation)}
     */
    @Deprecated
    public DoorLockProperty getDoorLockState(DoorLockProperty.Location location) {
        for (DoorLockProperty state : getDoorLockStates()) {
            if (state.getLocation() == location) return state;
        }

        return null;
    }

    /**
     * @return The lock and position states for all of the doors.
     */
    public DoorLockAndPositionState[] getDoorLockAndPositionStates() {
        return doorLockAndPositionStates;
    }

    /**
     * Get the lock and position state for a specific door.
     *
     * @param doorLocation The doorLocation of the door.
     * @return The lock and position state.
     */
    public DoorLockAndPositionState getDoorLockAndPositionState(DoorLocation doorLocation) {
        for (DoorLockAndPositionState lockAndPositionState : doorLockAndPositionStates) {
            if (lockAndPositionState.getDoorLocation() == doorLocation) return lockAndPositionState;
        }
        return null;
    }

    /**
     * @return The inside door lock states.
     */
    public DoorLockState[] getInsideDoorLockStates() {
        return insideDoorLockStates;
    }

    /**
     * Get the inside lock state for a specific door.
     *
     * @param doorLocation The door doorLocation.
     * @return The inside lock state.
     */
    public DoorLockState getInsideDoorLockState(DoorLocation doorLocation) {
        for (DoorLockState insideLockState : insideDoorLockStates) {
            if (insideLockState.getDoorLocation() == doorLocation) return insideLockState;
        }

        return null;
    }

    /**
     * @return The outside door lock states.
     */
    public DoorLockState[] getOutsideDoorLockStates() {
        return outsideDoorLockStates;
    }

    /**
     * Get the outside lock state for a specific door.
     *
     * @param doorLocation The door doorLocation.
     * @return The outside lock state.
     */
    public DoorLockState getOutsideDoorLockState(DoorLocation doorLocation) {
        for (DoorLockState outsideLockState : outsideDoorLockStates) {
            if (outsideLockState.getDoorLocation() == doorLocation) return outsideLockState;
        }
        return null;
    }

    /**
     * Get the lock state of the car.
     *
     * @return true if all doors are closed and locked, otherwise false.
     */
    public boolean isLocked() {
        DoorLockAndPositionState frontLeft = getDoorLockAndPositionState(DoorLocation.FRONT_LEFT);
        DoorLockAndPositionState frontRight = getDoorLockAndPositionState(DoorLocation.FRONT_RIGHT);
        DoorLockAndPositionState rearRight = getDoorLockAndPositionState(DoorLocation.REAR_RIGHT);
        DoorLockAndPositionState rearLeft = getDoorLockAndPositionState(DoorLocation.REAR_LEFT);

        if (frontLeft != null && frontLeft.getDoorLock() != LOCKED) return false;
        if (frontRight != null && frontRight.getDoorLock() != LOCKED) return false;
        if (rearRight != null && rearRight.getDoorLock() != LOCKED) return false;
        if (rearLeft != null && rearLeft.getDoorLock() != LOCKED) return false;

        return true;
    }

    public LockState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<DoorLockProperty> builder = new ArrayList<>();
        ArrayList<DoorLockAndPositionState> lockAndPositionStatesBuilder = new ArrayList<>();
        ArrayList<DoorLockState> insideLocksBuilder = new ArrayList<>();
        ArrayList<DoorLockState> outsideLocksBuilder = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case DoorLockAndPositionState.IDENTIFIER:
                    builder.add(new DoorLockProperty(property.getPropertyBytes()));
                    lockAndPositionStatesBuilder.add(new DoorLockAndPositionState(property
                            .getPropertyBytes()));
                    break;
                case INSIDE_LOCK_IDENTIFIER:
                    insideLocksBuilder.add(new DoorLockState(property.getPropertyBytes()));
                    break;
                case OUTSIDE_LOCK_IDENTIFIER:
                    outsideLocksBuilder.add(new DoorLockState(property.getPropertyBytes()));
                    break;
            }
        }

        lockStates = builder.toArray(new DoorLockProperty[builder.size()]);
        doorLockAndPositionStates = lockAndPositionStatesBuilder.toArray(new
                DoorLockAndPositionState[lockAndPositionStatesBuilder.size()]);
        insideDoorLockStates = insideLocksBuilder.toArray(new DoorLockState[insideLocksBuilder
                .size()]);
        outsideDoorLockStates = outsideLocksBuilder.toArray(new DoorLockState[outsideLocksBuilder
                .size()]);
    }

    @Override public boolean isState() {
        return true;
    }

    private LockState(Builder builder) {
        super(builder);
        lockStates = builder.states.toArray(new DoorLockProperty[builder.states.size()]);

        doorLockAndPositionStates = builder.lockAndPositionStates.toArray(new
                DoorLockAndPositionState[builder.lockAndPositionStates.size()]);
        insideDoorLockStates = builder.insideLockStates.toArray(new DoorLockState[builder
                .insideLockStates.size()]);
        outsideDoorLockStates = builder.outsideLockStates.toArray(new DoorLockState[builder
                .outsideLockStates.size()]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private List<DoorLockProperty> states = new ArrayList<>();

        private List<DoorLockAndPositionState> lockAndPositionStates = new ArrayList<>();
        private List<DoorLockState> insideLockStates = new ArrayList<>();
        private List<DoorLockState> outsideLockStates = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        /**
         * Set the door lock states.
         *
         * @param states The lock states.
         * @return The builder.
         * @deprecated use {@link #setLockAndPositionStates(DoorLockAndPositionState[])} instead.
         */
        @Deprecated
        public Builder setDoorLockStates(DoorLockProperty[] states) {
            this.states = Arrays.asList(states);

            for (int i = 0; i < states.length; i++) {
                addProperty(states[i]);
            }

            return this;
        }

        /**
         * Add one door lock state.
         *
         * @param state The lock state.
         * @return The builder.
         * @deprecated use {@link #addLockAndPositionState(DoorLockAndPositionState)} instead.
         */
        @Deprecated
        public Builder addDoorLockState(DoorLockProperty state) {
            addProperty(state);
            this.states.add(state);
            return this;
        }

        /**
         * @param lockAndPositionStates The lock and position states for all doors.
         * @return The builder.
         */
        public Builder setLockAndPositionStates(DoorLockAndPositionState[] lockAndPositionStates) {
            this.lockAndPositionStates = Arrays.asList(lockAndPositionStates);
            for (DoorLockAndPositionState lockAndPositionState : lockAndPositionStates) {
                addProperty(lockAndPositionState);
            }
            return this;
        }

        /**
         * Add the lock and position state for a door.
         *
         * @param lockAndPositionState The lock and position state.
         * @return The builder.
         */
        public Builder addLockAndPositionState(DoorLockAndPositionState lockAndPositionState) {
            this.lockAndPositionStates.add(lockAndPositionState);
            addProperty(lockAndPositionState);
            return this;
        }

        /**
         * @param insideLockStates The inside lock states for all of the doors.
         * @return The builder.
         */
        public Builder setInsideLockStates(DoorLockState[] insideLockStates) {
            this.insideLockStates = Arrays.asList(insideLockStates);
            for (DoorLockState insideLockState : insideLockStates) {
                insideLockState.setIdentifier(INSIDE_LOCK_IDENTIFIER);
                addProperty(insideLockState);
            }
            return this;
        }

        /**
         * Add the inside lock state for a door.
         *
         * @param insideLock The lock state.
         * @return The builder.
         */
        public Builder addInsideLockState(DoorLockState insideLock) {
            this.insideLockStates.add(insideLock);
            insideLock.setIdentifier(INSIDE_LOCK_IDENTIFIER);
            addProperty(insideLock);
            return this;
        }

        /**
         * @param outsideLockStates The outside lock states for all of the doors.
         * @return The builder.
         */
        public Builder setOutsideLockStates(DoorLockState[] outsideLockStates) {
            this.outsideLockStates = Arrays.asList(outsideLockStates);
            for (DoorLockState outsideLockState : outsideLockStates) {
                outsideLockState.setIdentifier(OUTSIDE_LOCK_IDENTIFIER);
                addOutsideLockState(outsideLockState);
            }
            return this;
        }

        /**
         * Add the outside lock state for a door.
         *
         * @param outsideLock The lock state.
         * @return The builder.
         */
        public Builder addOutsideLockState(DoorLockState outsideLock) {
            this.outsideLockStates.add(outsideLock);
            outsideLock.setIdentifier(OUTSIDE_LOCK_IDENTIFIER);
            addProperty(outsideLock);
            return this;
        }

        public LockState build() {
            return new LockState(this);
        }
    }
}