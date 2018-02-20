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
public class LockState extends CommandWithExistingProperties {
    public static final Type TYPE = new Type(Identifier.DOOR_LOCKS, 0x01);

    DoorLockProperty[] lockStates;

    /**
     * @return Set of lock states for each of the doors.
     */
    public DoorLockProperty[] getLockStates() {
        return lockStates;
    }

    /**
     * Get the door lock state for for door location
     *
     * @param location The location of the door
     * @return The DoorLockProperty
     */
    public DoorLockProperty getDoorLockState(DoorLockProperty.Location location) {
        for (DoorLockProperty state : getLockStates()) {
            if (state.getLocation() == location) return state;
        }

        return null;
    }

    /**
     * @return true if all doors are closed and locked, otherwise false
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

    private LockState(Builder builder) {
        super(builder);
        lockStates = builder.states.toArray(new DoorLockProperty[builder.states.size()]);
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private List<DoorLockProperty> states = new ArrayList<>();

        public Builder() {
            super(TYPE);
        }

        public Builder setDoorStates(DoorLockProperty[] states) {
            this.states.addAll(Arrays.asList(states));

            for (int i = 0; i < states.length; i++) {
                addProperty(states[i]);
            }

            return this;
        }

        public Builder addDoorState(DoorLockProperty state) {
            addProperty(state);
            this.states.add(state);
            return this;
        }

        public LockState build() {
            return new LockState(this);
        }
    }
}