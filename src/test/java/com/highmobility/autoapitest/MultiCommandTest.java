package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.LockState;
import com.highmobility.autoapi.LockUnlockDoors;
import com.highmobility.autoapi.MultiCommand;
import com.highmobility.autoapi.MultiState;
import com.highmobility.autoapi.SetTheftAlarm;
import com.highmobility.autoapi.TheftAlarmState;
import com.highmobility.autoapi.property.doors.DoorLocation;
import com.highmobility.autoapi.property.doors.DoorLock;
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

/**
 * Created by ttiganik on 15/09/16.
 */
public class MultiCommandTest {
    Bytes commandBytes = new Bytes(
            "001302" +
                    "01000400200201" +
                    "01000400460201");

    Bytes stateBytes = new Bytes(
            "001301" +
                    "01005E0020010100030001000100030100000100030200010100030300010200020001020002010003000202010300020300" +
                    "01000700460101000101");

    @Test
    public void commandIncoming() {
        Command command = CommandResolver.resolve(commandBytes);
        MultiCommand state = (MultiCommand) command;
        assertTrue(state.getCommands().length == 2);
        assertTrue(((LockUnlockDoors) state.getCommand(LockUnlockDoors.TYPE)).getDoorLock() == DoorLock.LOCKED);
        assertTrue(((SetTheftAlarm) state.getCommand(SetTheftAlarm.TYPE)).getState() == TheftAlarmState.State.ARMED);
    }

    @Test
    public void commandBuild() {
        Command[] commands = new Command[2];
        commands[0] = new LockUnlockDoors(DoorLock.LOCKED);
        commands[1] = new SetTheftAlarm(TheftAlarmState.State.ARMED);
        MultiCommand command = new MultiCommand(commands);
        assertTrue(command.equals(commandBytes));
    }

    @Test
    public void stateIncoming() {
        Command command = CommandResolver.resolve(stateBytes);
        MultiState state = (MultiState) command;
        assertTrue(state.getCommands().length == 2);

        LockState lockState = (LockState) state.getCommand(LockState.TYPE);
        assertTrue(lockState.getInsideDoorLockState(DoorLocation.FRONT_LEFT).getDoorLock() == LOCKED);
        TheftAlarmState theftAlarmState = (TheftAlarmState) state.getCommand(TheftAlarmState.TYPE);
        assertTrue(theftAlarmState.getState() == TheftAlarmState.State.ARMED);
    }

    @Test
    public void stateBuild() {
        Command[] commands = new Command[2];

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
        LockState lockState = builder.build();

        TheftAlarmState.Builder tBuilder = new TheftAlarmState.Builder();
        tBuilder.setState(TheftAlarmState.State.ARMED);
        TheftAlarmState theftAlarmState = tBuilder.build();

        commands[0] = lockState;
        commands[1] = theftAlarmState;

        MultiState command = new MultiState(commands);
        assertTrue(command.equals(stateBytes));
    }
}