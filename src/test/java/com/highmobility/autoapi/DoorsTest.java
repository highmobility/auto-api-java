package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.DoorPosition;
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.Lock;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.Position;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoorsTest extends BaseTest {
    Bytes bytes = new Bytes(
            "002001" +
                    "0200050100020000" +
                    "0200050100020100" +
                    "0300050100020001" +
                    "0300050100020101" +
                    "0400050100020001" +
                    "0400050100020100" +
                    "0400050100020200" +
                    "0400050100020300" +
                    "06000401000101"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command instanceof DoorsState);
        testState((DoorsState) command);
    }

    @Test public void build() {
        DoorsState.Builder builder = new DoorsState.Builder();

        builder.addInsideLock(new Property(new Lock(Location.FRONT_LEFT, LockState.UNLOCKED)));
        builder.addInsideLock(new Property(new Lock(Location.FRONT_RIGHT, LockState.UNLOCKED)));
        builder.addLock(new Property(new Lock(Location.FRONT_LEFT, LockState.LOCKED)));
        builder.addLock(new Property(new Lock(Location.FRONT_RIGHT, LockState.LOCKED)));
        builder.addPosition(new Property(new DoorPosition(DoorPosition.Location.FRONT_LEFT,
                Position.OPEN)));
        builder.addPosition(new Property(new DoorPosition(DoorPosition.Location.FRONT_RIGHT,
                Position.CLOSED)));
        builder.addPosition(new Property(new DoorPosition(DoorPosition.Location.REAR_RIGHT,
                Position.CLOSED)));
        builder.addPosition(new Property(new DoorPosition(DoorPosition.Location.REAR_LEFT,
                Position.CLOSED)));
        builder.setLocksState(new Property(LockState.LOCKED));

        DoorsState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));
        testState(state);
    }

    void testState(DoorsState state) {
        assertTrue(state.getPositions().length == 4);
        assertTrue(state.getLocks().length == 2);
        assertTrue(state.getInsideLocks().length == 2);

        assertTrue(state.getInsideLock(Location.FRONT_LEFT).getValue().getLockState() == LockState.UNLOCKED);
        assertTrue(state.getInsideLock(Location.FRONT_RIGHT).getValue().getLockState() == LockState.UNLOCKED);

        assertTrue(state.getLock(Location.FRONT_LEFT).getValue().getLockState() == LockState.LOCKED);
        assertTrue(state.getLock(Location.FRONT_RIGHT).getValue().getLockState() == LockState.LOCKED);
        assertTrue(state.getLocksState().getValue() == LockState.LOCKED);

        assertTrue(state.getPosition(DoorPosition.Location.FRONT_LEFT).getValue().getPosition() == Position.OPEN);
        assertTrue(state.getPosition(DoorPosition.Location.FRONT_RIGHT).getValue().getPosition() == Position.CLOSED);
        assertTrue(state.getPosition(DoorPosition.Location.REAR_RIGHT).getValue().getPosition() == Position.CLOSED);
        assertTrue(state.getPosition(DoorPosition.Location.REAR_LEFT).getValue().getPosition() == Position.CLOSED);

        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        Bytes bytes = new Bytes("002000");
        Bytes commandBytes = new GetDoorsState();
        assertTrue(bytes.equals(commandBytes));

        Command command = CommandResolver.resolve(bytes);
        assertTrue(command instanceof GetDoorsState);
    }

    @Test public void lock() {
        Bytes waitingForBytes = new Bytes("002001" +
                "05000401000101");
        Command commandBytes = new LockUnlockDoors(LockState.LOCKED);
        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof LockUnlockDoors);
        LockUnlockDoors state = (LockUnlockDoors) command;
        assertTrue(state.getInsideLocksState().getValue() == LockState.LOCKED);
    }

    /*@Test public void allLocksValue() {
        // TODO: 24/09/2019 verify when fixe/mikk has answer.
        //  currently have different door locations. one has ALL, the other doesnt
        Bytes bytes = new Bytes(
                "002001" +
                        "0300050100020501");
        DoorsState state = (DoorsState) CommandResolver.resolve(bytes);
        assertTrue(state.getLocks().length == 1);
        assertTrue(getLock(state.getLocks(), Location.ALL).getLock() == LOCKED);
        assertTrue(state.isLocked());
    }*/
}
