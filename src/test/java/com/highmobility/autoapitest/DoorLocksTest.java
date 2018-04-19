package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetLockState;
import com.highmobility.autoapi.LockState;
import com.highmobility.autoapi.LockUnlockDoors;
import com.highmobility.autoapi.property.DoorLockProperty;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DoorLocksTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "002001010003000100010003010000010003020001010003030001");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(LockState.TYPE));
        LockState state = (LockState) command;
        assertTrue(state.getDoorLockStates().length == 4);
        boolean leftExists = false, rightExist = false, rearLeftExists = false, rearRightExists =
                false;

        for (DoorLockProperty tireState : state.getDoorLockStates()) {
            switch (tireState.getLocation()) {
                case FRONT_LEFT:
                    leftExists = true;
                    assertTrue(tireState.getLockState() == DoorLockProperty.LockState.UNLOCKED);
                    assertTrue(tireState.getPosition() == DoorLockProperty.Position.OPEN);
                    break;
                case FRONT_RIGHT:
                    rightExist = true;
                    assertTrue(tireState.getLockState() == DoorLockProperty.LockState.UNLOCKED);
                    assertTrue(tireState.getPosition() == DoorLockProperty.Position.CLOSED);
                    break;
                case REAR_RIGHT:
                    rearRightExists = true;
                    assertTrue(tireState.getLockState() == DoorLockProperty.LockState.LOCKED);
                    assertTrue(tireState.getPosition() == DoorLockProperty.Position.CLOSED);
                    break;
                case REAR_LEFT:
                    rearLeftExists = true;
                    assertTrue(tireState.getLockState() == DoorLockProperty.LockState.LOCKED);
                    assertTrue(tireState.getPosition() == DoorLockProperty.Position.CLOSED);
                    break;
            }
        }

        assertTrue(leftExists == true);
        assertTrue(rightExist == true);
        assertTrue(rearRightExists == true);
        assertTrue(rearLeftExists == true);
    }

    @Test public void get() {
        byte[] waitingForBytes = Bytes.bytesFromHex("002000");
        byte[] commandBytes = new GetLockState().getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof GetLockState);
    }

    @Test public void lock() {
        byte[] waitingForBytes = Bytes.bytesFromHex("00200201");
        byte[] commandBytes = new LockUnlockDoors(DoorLockProperty.LockState.LOCKED).getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof LockUnlockDoors);

        LockUnlockDoors state = (LockUnlockDoors) command;
        assertTrue(state.getLockState() == DoorLockProperty.LockState.LOCKED);

    }

    @Test public void create() {
        LockState.Builder builder = new LockState.Builder();
        builder.addDoorLockState(new DoorLockProperty(DoorLockProperty.Location.FRONT_LEFT,
                DoorLockProperty.Position.OPEN,
                DoorLockProperty.LockState.UNLOCKED));

        builder.addDoorLockState(new DoorLockProperty(DoorLockProperty.Location.FRONT_RIGHT,
                DoorLockProperty.Position.CLOSED,
                DoorLockProperty.LockState.UNLOCKED));

        builder.addDoorLockState(new DoorLockProperty(DoorLockProperty.Location.REAR_RIGHT,
                DoorLockProperty.Position.CLOSED,
                DoorLockProperty.LockState.LOCKED));

        builder.addDoorLockState(new DoorLockProperty(DoorLockProperty.Location.REAR_LEFT,
                DoorLockProperty.Position.CLOSED,
                DoorLockProperty.LockState.LOCKED));

        assertTrue(Arrays.equals(builder.build().getBytes(), Bytes.bytesFromHex
                ("002001010003000100010003010000010003020001010003030001")));
    }

    @Test public void state0Properties() {
        byte[] bytes = Bytes.bytesFromHex("002001");
        LockState state = (LockState) CommandResolver.resolve(bytes);
        assertTrue(state.getDoorLockStates().length == 0);
    }
}
