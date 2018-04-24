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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is an evented message that is sent from the car every time the lock state changes. This
 * message is also sent when a Get Lock State is received by the car. The new status is included in
 * the message payload and may be the result of user, device or car triggered action.
 */
public class LockState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.DOOR_LOCKS, 0x01);

    DoorLockProperty[] lockStates;

    /**
     * @return Set of lock states for each of the doors.
     * @deprecated use {@link #getDoorLockStates()} instead.
     */
    @Deprecated
    public DoorLockProperty[] getLockStates() {
        return lockStates;
    }

    /**
     * @return All of the lock states.
     */
    public DoorLockProperty[] getDoorLockStates() {
        return lockStates;
    }

    /**
     * Get the lock state for a specific door.
     *
     * @param location The location of the door
     * @return The DoorLockProperty
     */
    public DoorLockProperty getDoorLockState(DoorLockProperty.Location location) {
        for (DoorLockProperty state : getDoorLockStates()) {
            if (state.getLocation() == location) return state;
        }

        return null;
    }

    /**
     * Get the lock state of the car.
     *
     * @return true if all doors are closed and locked, otherwise false.
     */
    public boolean isLocked() {
        DoorLockProperty frontLeft = getDoorLockState(DoorLockProperty.Location.FRONT_LEFT);
        DoorLockProperty frontRight = getDoorLockState(DoorLockProperty.Location.FRONT_RIGHT);
        DoorLockProperty rearRight = getDoorLockState(DoorLockProperty.Location.REAR_RIGHT);
        DoorLockProperty rearLeft = getDoorLockState(DoorLockProperty.Location.REAR_LEFT);

        if (frontLeft != null && frontLeft.getLockState() != DoorLockProperty.LockState.LOCKED) {
            return false;
        }

        if (frontRight != null && frontRight.getLockState() != DoorLockProperty.LockState.LOCKED) {
            return false;
        }

        if (rearRight != null && rearRight.getLockState() != DoorLockProperty.LockState.LOCKED) {
            return false;
        }

        if (rearLeft != null && rearLeft.getLockState() != DoorLockProperty.LockState.LOCKED) {
            return false;
        }

        return true;
    }

    public LockState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<DoorLockProperty> builder = new ArrayList<>();
        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:

                    builder.add(new DoorLockProperty(property.getPropertyBytes()));
                    break;
            }
        }

        lockStates = builder.toArray(new DoorLockProperty[builder.size()]);
    }

    @Override public boolean isState() {
        return true;
    }

    private LockState(Builder builder) {
        super(builder);
        lockStates = builder.states.toArray(new DoorLockProperty[builder.states.size()]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private List<DoorLockProperty> states = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        /**
         * Add an array of door lock states.
         *
         * @param states The lock states.
         * @return The builder.
         */
        public Builder addDoorLockStates(DoorLockProperty[] states) {
            this.states.addAll(Arrays.asList(states));

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
         */
        public Builder addDoorLockState(DoorLockProperty state) {
            addProperty(state);
            this.states.add(state);
            return this;
        }

        /**
         * Add an array of door lock states.
         *
         * @param states The states
         * @return The builder
         * @deprecated use {@link #addDoorLockStates(DoorLockProperty[])} instead.
         */
        @Deprecated
        public Builder setDoorStates(DoorLockProperty[] states) {
            return addDoorLockStates(states);
        }

        /**
         * Add one door lock state.
         *
         * @param state The lock state.
         * @return The buidler.
         * @deprecated use {@link #addDoorLockState(DoorLockProperty)} instead.
         */
        @Deprecated
        public Builder addDoorState(DoorLockProperty state) {
            return addDoorLockState(state);
        }

        public LockState build() {
            return new LockState(this);
        }
    }
}