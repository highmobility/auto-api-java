package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetLockState;
import com.highmobility.autoapi.LockState;
import com.highmobility.autoapi.LockUnlockDoors;
import com.highmobility.autoapi.value.Position;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.doors.DoorLockState;
import com.highmobility.autoapi.value.doors.DoorPosition;
import com.highmobility.autoapi.value.Location;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static com.highmobility.autoapi.value.Location.FRONT_LEFT;
import static com.highmobility.autoapi.value.Location.FRONT_RIGHT;
import static com.highmobility.autoapi.value.Location.REAR_LEFT;
import static com.highmobility.autoapi.value.Location.REAR_RIGHT;
import static com.highmobility.autoapi.value.Lock.LOCKED;
import static com.highmobility.autoapi.value.Lock.UNLOCKED;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoorLocksTest {
    Bytes bytes = new Bytes(
            "002001" +
                    "0200050100020000" +
                    "0200050100020100" +
                    "0300050100020001" +
                    "0300050100020101" +
                    "0400050100020001" +
                    "0400050100020100" +
                    "0400050100020200" +
                    "0400050100020300"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.is(LockState.TYPE));
        testState((LockState) command);
    }

    @Test public void build() {
        LockState.Builder builder = new LockState.Builder();

        builder.addInsideLock(new Property(new DoorLockState(FRONT_LEFT, UNLOCKED)));
        builder.addInsideLock(new Property(new DoorLockState(FRONT_RIGHT, UNLOCKED)));
        builder.addOutsideLock(new Property(new DoorLockState(FRONT_LEFT, LOCKED)));
        builder.addOutsideLock(new Property(new DoorLockState(FRONT_RIGHT, LOCKED)));
        builder.addPosition(new Property(new DoorPosition(FRONT_LEFT, Position.OPEN)));
        builder.addPosition(new Property(new DoorPosition(FRONT_RIGHT, Position.CLOSED)));
        builder.addPosition(new Property(new DoorPosition(REAR_RIGHT, Position.CLOSED)));
        builder.addPosition(new Property(new DoorPosition(REAR_LEFT, Position.CLOSED)));

        LockState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));
        testState(state);
    }

    void testState(LockState state) {
        assertTrue(state.getPositions().length == 4);
        assertTrue(state.getOutsideLocks().length == 2);
        assertTrue(state.getInsideLocks().length == 2);

        assertTrue(state.getInsideLock(FRONT_LEFT).getValue().getLock() == UNLOCKED);
        assertTrue(state.getInsideLock(FRONT_RIGHT).getValue().getLock() == UNLOCKED);

        assertTrue(state.getOutsideLock(FRONT_LEFT).getValue().getLock() == LOCKED);
        assertTrue(state.getOutsideLock(FRONT_RIGHT).getValue().getLock() == LOCKED);
        assertTrue(state.isLocked());

        assertTrue(state.getPosition(FRONT_LEFT).getValue().getPosition() == Position.OPEN);
        assertTrue(state.getPosition(FRONT_RIGHT).getValue().getPosition() == Position.CLOSED);
        assertTrue(state.getPosition(REAR_RIGHT).getValue().getPosition() == Position.CLOSED);
        assertTrue(state.getPosition(REAR_LEFT).getValue().getPosition() == Position.CLOSED);
    }

    @Test public void get() {
        Bytes bytes = new Bytes("002000");
        Bytes commandBytes = new GetLockState();
        assertTrue(bytes.equals(commandBytes));

        Command command = CommandResolver.resolve(bytes);
        assertTrue(command instanceof GetLockState);
    }

    @Test public void lock() {
        Bytes waitingForBytes = new Bytes("00201201000401000101");
        byte[] commandBytes = new LockUnlockDoors(LOCKED).getByteArray();
        assertTrue(waitingForBytes.equals(commandBytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof LockUnlockDoors);

        LockUnlockDoors state = (LockUnlockDoors) command;
        assertTrue(state.getDoorLock().getValue() == LOCKED);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("002001");
        LockState state = (LockState) CommandResolver.resolve(bytes);
        assertTrue(state.getOutsideLocks().length == 0);
    }

    @Test public void allLocksValue() {
        Bytes bytes = new Bytes(
                "002001" +
                        "0300050100020501");
        LockState state = (LockState) CommandResolver.resolve(bytes);
        assertTrue(state.getOutsideLocks().length == 1);
        assertTrue(state.getOutsideLock(Location.ALL).getValue().getLock() == LOCKED);
        assertTrue(state.isLocked());
    }

    @Test public void failsWherePropertiesMandatory() {
        assertTrue(CommandResolver.resolve(LockUnlockDoors.TYPE.getIdentifierAndType()).getClass() == Command.class);
    }
}
