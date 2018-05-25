package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetLockState;
import com.highmobility.autoapi.LockState;
import com.highmobility.autoapi.LockUnlockDoors;
import com.highmobility.autoapi.property.doors.DoorLockAndPositionState;
import com.highmobility.autoapi.property.doors.DoorLockState;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static com.highmobility.autoapi.property.doors.DoorLocation.FRONT_LEFT;
import static com.highmobility.autoapi.property.doors.DoorLocation.FRONT_RIGHT;
import static com.highmobility.autoapi.property.doors.DoorLocation.REAR_LEFT;
import static com.highmobility.autoapi.property.doors.DoorLocation.REAR_RIGHT;
import static com.highmobility.autoapi.property.doors.DoorLock.LOCKED;
import static com.highmobility.autoapi.property.doors.DoorLock.UNLOCKED;
import static com.highmobility.autoapi.property.doors.DoorPosition.CLOSED;
import static com.highmobility.autoapi.property.doors.DoorPosition.OPEN;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DoorLocksTest {
    Bytes bytes = new Bytes(
            "002001010003000100010003010000010003020001010003030001" +
                    "0200020001" + "0200020100" + // front left and right inside locks
                    "0300020201" + "0300020300"); // rear right and left outside locks

    @Test
    public void state() {
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(LockState.TYPE));
        LockState state = (LockState) command;
        assertTrue(state.getDoorLockAndPositionStates().length == 4);
        boolean leftExists = false, rightExist = false, rearLeftExists = false, rearRightExists =
                false;

        for (DoorLockAndPositionState tireState : state.getDoorLockAndPositionStates()) {
            switch (tireState.getDoorLocation()) {
                case FRONT_LEFT:
                    leftExists = true;
                    assertTrue(tireState.getDoorLock() == UNLOCKED);
                    assertTrue(tireState.getDoorPosition() == OPEN);
                    break;
                case FRONT_RIGHT:
                    rightExist = true;
                    assertTrue(tireState.getDoorLock() == UNLOCKED);
                    assertTrue(tireState.getDoorPosition() == CLOSED);
                    break;
                case REAR_RIGHT:
                    rearRightExists = true;
                    assertTrue(tireState.getDoorLock() == LOCKED);
                    assertTrue(tireState.getDoorPosition() == CLOSED);
                    break;
                case REAR_LEFT:
                    rearLeftExists = true;
                    assertTrue(tireState.getDoorLock() == LOCKED);
                    assertTrue(tireState.getDoorPosition() == CLOSED);
                    break;
            }
        }

        assertTrue(leftExists == true);
        assertTrue(rightExist == true);
        assertTrue(rearRightExists == true);
        assertTrue(rearLeftExists == true);

        assertTrue(state.getInsideDoorLockStates().length == 2);
        assertTrue(state.getInsideDoorLockState(FRONT_LEFT).getDoorLock() == LOCKED);
        assertTrue(state.getInsideDoorLockState(FRONT_RIGHT).getDoorLock() == UNLOCKED);

        assertTrue(state.getOutsideDoorLockStates().length == 2);
        assertTrue(state.getOutsideDoorLockState(REAR_RIGHT).getDoorLock() == LOCKED);
        assertTrue(state.getOutsideDoorLockState(REAR_LEFT).getDoorLock() == UNLOCKED);
    }

    @Test public void build() {
        LockState.Builder builder = new LockState.Builder();
        builder.addLockAndPositionState(new DoorLockAndPositionState(FRONT_LEFT, OPEN, UNLOCKED));
        builder.addLockAndPositionState(new DoorLockAndPositionState(FRONT_RIGHT, CLOSED,
                UNLOCKED));
        builder.addLockAndPositionState(new DoorLockAndPositionState(REAR_RIGHT, CLOSED, LOCKED));
        builder.addLockAndPositionState(new DoorLockAndPositionState(REAR_LEFT, CLOSED, LOCKED));

        builder.addInsideLockState(new DoorLockState(FRONT_LEFT, LOCKED));
        builder.addInsideLockState(new DoorLockState(FRONT_RIGHT, UNLOCKED));
        builder.addOutsideLockState(new DoorLockState(REAR_RIGHT, LOCKED));
        builder.addOutsideLockState(new DoorLockState(REAR_LEFT, UNLOCKED));

        assertTrue(builder.build().equals(bytes));
    }

    @Test public void get() {
        Bytes bytes = new Bytes("002000");
        Bytes commandBytes = new GetLockState();
        assertTrue(bytes.equals(commandBytes));

        Command command = CommandResolver.resolve(bytes);
        assertTrue(command instanceof GetLockState);
    }

    @Test public void lock() {
        Bytes waitingForBytes = new Bytes("00200201");
        byte[] commandBytes = new LockUnlockDoors(LOCKED).getByteArray();
        assertTrue(waitingForBytes.equals(commandBytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof LockUnlockDoors);

        LockUnlockDoors state = (LockUnlockDoors) command;
        assertTrue(state.getDoorLock() == LOCKED);

    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("002001");
        LockState state = (LockState) CommandResolver.resolve(bytes);
        assertTrue(state.getDoorLockAndPositionStates().length == 0);
    }
}
