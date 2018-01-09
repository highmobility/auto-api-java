package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.DoorLockState;
import com.highmobility.autoapi.GetLockState;
import com.highmobility.autoapi.LockState;
import com.highmobility.autoapi.LockUnlockDoors;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.concurrent.locks.Lock;

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
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.is(LockState.TYPE));
        LockState state = (LockState) command;
        assertTrue(state.getLockStates().size() == 4);
        boolean leftExists = false, rightExist = false, rearLeftExists = false, rearRightExists = false;

        for (DoorLockState tireState : state.getLockStates()) {
            switch (tireState.getLocation()) {
                case FRONT_LEFT:
                    leftExists = true;
                    assertTrue(tireState.getLockState() == DoorLockState.LockState.UNLOCKED);
                    assertTrue(tireState.getPosition() == DoorLockState.Position.OPEN);
                    break;
                case FRONT_RIGHT:
                    rightExist = true;
                    assertTrue(tireState.getLockState() == DoorLockState.LockState.UNLOCKED);
                    assertTrue(tireState.getPosition() == DoorLockState.Position.CLOSED);
                    break;
                case REAR_RIGHT:
                    rearRightExists = true;
                    assertTrue(tireState.getLockState() == DoorLockState.LockState.LOCKED);
                    assertTrue(tireState.getPosition() == DoorLockState.Position.CLOSED);
                    break;
                case REAR_LEFT:
                    rearLeftExists = true;
                    assertTrue(tireState.getLockState() == DoorLockState.LockState.LOCKED);
                    assertTrue(tireState.getPosition() == DoorLockState.Position.CLOSED);
                    break;
            }
        }

        assertTrue(leftExists == true);
        assertTrue(rightExist == true);
        assertTrue(rearRightExists == true);
        assertTrue(rearLeftExists == true);
    }

    @Test public void get() {
        String waitingForBytes = "002000";
        String commandBytes = Bytes.hexFromBytes(new GetLockState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void lock() {
        String waitingForBytes = "00200201";
        String commandBytes = Bytes.hexFromBytes(new LockUnlockDoors(DoorLockState.LockState.LOCKED).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}
