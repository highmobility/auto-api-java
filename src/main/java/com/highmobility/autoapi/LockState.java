package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This is an evented message that is sent from the car every time the lock state changes. This
 * message is also sent when a Get Lock State is received by the car. The new status is included in
 * the message payload and may be the result of user, device or car triggered action.
 */
public class LockState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.DOOR_LOCKS, 0x01);

    Set<DoorLockState> lockStates;

    /**
     * @return Set of lock states for each of the doors.
     */
    public Set<DoorLockState> getLockStates() {
        return lockStates;
    }

    /**
     * Get the door lock state for for door location
     *
     * @param location The location of the door
     * @return The DoorLockState
     */
    public DoorLockState getDoorLockState(DoorLockState.Location location) {
        for (DoorLockState state : getLockStates()) {
            if (state.getLocation() == location) return state;
        }

        return null;
    }

    /**
     * @return true if all doors are closed and locked, otherwise false
     */
    public boolean isLocked() {
        DoorLockState frontLeft = getDoorLockState(DoorLockState.Location.FRONT_LEFT);
        DoorLockState frontRight = getDoorLockState(DoorLockState.Location.FRONT_RIGHT);
        DoorLockState rearRight = getDoorLockState(DoorLockState.Location.REAR_RIGHT);
        DoorLockState rearLeft = getDoorLockState(DoorLockState.Location.REAR_LEFT);

        if (frontLeft != null && frontLeft.getLockState() != DoorLockState.LockState.LOCKED) {
            return false;
        }

        if (frontRight != null && frontRight.getLockState() != DoorLockState.LockState.LOCKED) {
            return false;
        }

        if (rearRight != null && rearRight.getLockState() != DoorLockState.LockState.LOCKED) {
            return false;
        }

        if (rearLeft != null && rearLeft.getLockState() != DoorLockState.LockState.LOCKED) {
            return false;
        }

        return true;
    }

    public LockState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    if (lockStates == null) lockStates = new HashSet<>();
                    lockStates.add(new DoorLockState(property.getValueBytes(), 0));
                    break;
            }
        }

        lockStates = Collections.unmodifiableSet(lockStates);
    }
}