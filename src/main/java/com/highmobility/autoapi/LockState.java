package com.highmobility.autoapi;

import com.highmobility.autoapi.property.DoorLockProperty;
import com.highmobility.autoapi.property.Property;

import java.util.ArrayList;

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
        super(TYPE, builder.getProperties());
        lockStates = builder.states;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private DoorLockProperty[] states;

        public Builder() {
            super(TYPE);
        }

        public Builder setDoorStates(DoorLockProperty[] states) {
            this.states = states;
            for (int i = 0; i < states.length; i++) {
                addProperty(states[i]);
            }
            return this;
        }

        public Builder addDoorState(DoorLockProperty state) {
            if (states == null) {
                states = new DoorLockProperty[1];
            }
            else {
                DoorLockProperty[] newStates = new DoorLockProperty[states.length + 1];
                for (int i = 0; i < states.length; i++) {
                    newStates[i] = states[i];
                }
                states = newStates;
            }

            addProperty(state);
            states[states.length - 1] = state;
            return this;
        }

        public LockState build() {
            return new LockState(this);
        }
    }
}