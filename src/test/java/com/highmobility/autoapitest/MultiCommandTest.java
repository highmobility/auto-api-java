package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.LockState;
import com.highmobility.autoapi.LockUnlockDoors;
import com.highmobility.autoapi.MultiCommand;
import com.highmobility.autoapi.MultiState;
import com.highmobility.autoapi.SetTheftAlarm;
import com.highmobility.autoapi.TheftAlarmState;
import com.highmobility.autoapi.value.Location;
import com.highmobility.autoapi.value.Lock;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class MultiCommandTest {
    Bytes commandBytes = new Bytes(
            "001302" +
                    "01000D01000A00201201000401000101" +
                    "01000D01000A00461201000401000101");

    Bytes stateBytes = new Bytes(
            "001301" +
                    "010046010043" +
                    "002001" + // 43 / 67
                    "0200050100020000" +
                    "0200050100020100" +
                    "0300050100020001" +
                    "0300050100020101" +
                    "0400050100020001" +
                    "0400050100020100" +
                    "0400050100020200" +
                    "0400050100020300" +

                    "01000D01000A00460101000401000101"); // d / 13

    @Test
    public void commandIncoming() {
        Command command = CommandResolver.resolve(commandBytes);
        MultiCommand state = (MultiCommand) command;
        assertTrue(state.getCommands().length == 2);
        LockUnlockDoors lockUnlockDoors =
                (LockUnlockDoors) state.getCommand(LockUnlockDoors.TYPE).getValue();
        SetTheftAlarm setTheftAlarm =
                (SetTheftAlarm) state.getCommand(SetTheftAlarm.TYPE).getValue();
        assertTrue(lockUnlockDoors.getDoorLock().getValue() == Lock.LOCKED);
        assertTrue(setTheftAlarm.getState().getValue() == TheftAlarmState.Value.ARMED);
    }

    @Test
    public void commandBuild() {
        Command[] commands = new Command[2];
        commands[0] = new LockUnlockDoors(Lock.LOCKED);
        commands[1] = new SetTheftAlarm(TheftAlarmState.Value.ARMED);
        MultiCommand command = new MultiCommand(commands);
        assertTrue(TestUtils.bytesTheSame(command, commandBytes));
    }

    @Test
    public void stateIncoming() {
        Command command = CommandResolver.resolve(stateBytes);
        MultiState state = (MultiState) command;
        assertTrue(state.getCommands().length == 2);

        LockState lockState = (LockState) state.getCommand(LockState.TYPE).getValue();
        assertTrue(lockState.getInsideLock(Location.FRONT_LEFT).getValue().getLock() == Lock.UNLOCKED);
        TheftAlarmState theftAlarmState =
                (TheftAlarmState) state.getCommand(TheftAlarmState.TYPE).getValue();
        assertTrue(theftAlarmState.getState().getValue() == TheftAlarmState.Value.ARMED);
    }

    // TBODO:
    /*@Test
    public void stateBuild() {
        Command[] commands = new Command[2];

        LockState.Builder builder = new LockState.Builder();
        builder.addInsideLock(new DoorLockState(FRONT_LEFT, UNLOCKED));
        builder.addInsideLock(new DoorLockState(FRONT_RIGHT, UNLOCKED));
        builder.addLock(new DoorLockState(FRONT_LEFT, LOCKED));
        builder.addLock(new DoorLockState(FRONT_RIGHT, LOCKED));
        builder.addPosition(new DoorPosition(FRONT_LEFT, Position.OPEN));
        builder.addPosition(new DoorPosition(FRONT_RIGHT, Position.CLOSED));
        builder.addPosition(new DoorPosition(REAR_RIGHT, Position.CLOSED));
        builder.addPosition(new DoorPosition(REAR_LEFT, Position.CLOSED));
        LockState lockState = builder.build();

        TheftAlarmState.Builder tBuilder = new TheftAlarmState.Builder();
        tBuilder.setState(TheftAlarmState.State.ARMED);
        TheftAlarmState theftAlarmState = tBuilder.build();

        commands[0] = lockState;
        commands[1] = theftAlarmState;

        MultiState command = new MultiState(commands);
        assertTrue(TestUtils.bytesTheSame(command, stateBytes));
    }*/
}