package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetLockState;
import com.highmobility.autoapi.LockState;
import com.highmobility.autoapi.LockUnlockDoors;
import com.highmobility.autoapi.property.doors.DoorLockState;
import com.highmobility.autoapi.property.doors.DoorPosition;
import com.highmobility.autoapi.property.value.Position;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static com.highmobility.autoapi.property.doors.DoorLocation.FRONT_LEFT;
import static com.highmobility.autoapi.property.doors.DoorLocation.FRONT_RIGHT;
import static com.highmobility.autoapi.property.doors.DoorLocation.REAR_LEFT;
import static com.highmobility.autoapi.property.doors.DoorLocation.REAR_RIGHT;
import static com.highmobility.autoapi.property.doors.DoorLock.LOCKED;
import static com.highmobility.autoapi.property.doors.DoorLock.UNLOCKED;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DoorLocksTest {
    Bytes bytes = new Bytes(
            "002001" +
                    "0200020000" +
                    "0200020100" +
                    "0300020001" +
                    "0300020101" +
                    "0400020001" +
                    "0400020100" +
                    "0400020200" +
                    "0400020300");

    @Test
    public void state() {
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(LockState.TYPE));
        testState((LockState) command);
    }

    @Test public void build() {
        LockState.Builder builder = new LockState.Builder();

        builder.addInsideLock(new DoorLockState(FRONT_LEFT, UNLOCKED));
        builder.addInsideLock(new DoorLockState(FRONT_RIGHT, UNLOCKED));
        builder.addLock(new DoorLockState(FRONT_LEFT, LOCKED));
        builder.addLock(new DoorLockState(FRONT_RIGHT, LOCKED));
        builder.addPosition(new DoorPosition(FRONT_LEFT, Position.OPEN));
        builder.addPosition(new DoorPosition(FRONT_RIGHT, Position.CLOSED));
        builder.addPosition(new DoorPosition(REAR_RIGHT, Position.CLOSED));
        builder.addPosition(new DoorPosition(REAR_LEFT, Position.CLOSED));

        LockState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));
        testState(state);
    }

    void testState(LockState state) {
        assertTrue(state.getPositions().length == 4);
        assertTrue(state.getLocks().length == 2);
        assertTrue(state.getInsideLocks().length == 2);

        assertTrue(state.getInsideLock(FRONT_LEFT).getLock() == UNLOCKED);
        assertTrue(state.getInsideLock(FRONT_RIGHT).getLock() == UNLOCKED);

        assertTrue(state.getLock(FRONT_LEFT).getLock() == LOCKED);
        assertTrue(state.getLock(FRONT_RIGHT).getLock() == LOCKED);
        assertTrue(state.isLocked());

        assertTrue(state.getPosition(FRONT_LEFT).getPosition() == Position.OPEN);
        assertTrue(state.getPosition(FRONT_RIGHT).getPosition() == Position.CLOSED);
        assertTrue(state.getPosition(REAR_RIGHT).getPosition() == Position.CLOSED);
        assertTrue(state.getPosition(REAR_LEFT).getPosition() == Position.CLOSED);
    }

    @Test public void get() {
        Bytes bytes = new Bytes("002000");
        Bytes commandBytes = new GetLockState();
        assertTrue(bytes.equals(commandBytes));

        Command command = CommandResolver.resolve(bytes);
        assertTrue(command instanceof GetLockState);
    }

    @Test public void lock() {
        Bytes waitingForBytes = new Bytes("00201201000101");
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
        assertTrue(state.getLocks().length == 0);
    }
}
