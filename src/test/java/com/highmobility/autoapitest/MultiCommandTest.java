package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.LockState;
import com.highmobility.autoapi.LockUnlockDoors;
import com.highmobility.autoapi.MultiCommand;
import com.highmobility.autoapi.MultiState;
import com.highmobility.autoapi.SetTheftAlarm;
import com.highmobility.autoapi.TheftAlarmState;
import com.highmobility.autoapi.property.value.Location;
import com.highmobility.autoapi.property.value.Lock;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class MultiCommandTest {
    Bytes commandBytes = new Bytes(
            "001302" +
                    "01000700201201000101" +
                    "01000700461201000101");

    Bytes stateBytes = new Bytes(
            "001301" +
                    "01002B00200102000200000200020100030002000103000201010400020001040002010004000202000400020300" +
                    "01000700460101000101");

    @Test
    public void commandIncoming() {
        Command command = CommandResolver.resolve(commandBytes);
        MultiCommand state = (MultiCommand) command;
        assertTrue(state.getCommands().length == 2);
        assertTrue(((LockUnlockDoors) state.getCommand(LockUnlockDoors.TYPE)).getDoorLock() == Lock.LOCKED);
        assertTrue(((SetTheftAlarm) state.getCommand(SetTheftAlarm.TYPE)).getState() == TheftAlarmState.State.ARMED);
    }

    @Test
    public void commandBuild() {
        Command[] commands = new Command[2];
        commands[0] = new LockUnlockDoors(Lock.LOCKED);
        commands[1] = new SetTheftAlarm(TheftAlarmState.State.ARMED);
        MultiCommand command = new MultiCommand(commands);
        assertTrue(TestUtils.bytesTheSame(command, commandBytes));
    }

    @Test
    public void stateIncoming() {
        Command command = CommandResolver.resolve(stateBytes);
        MultiState state = (MultiState) command;
        assertTrue(state.getCommands().length == 2);

        LockState lockState = (LockState) state.getCommand(LockState.TYPE);
        assertTrue(lockState.getInsideLock(Location.FRONT_LEFT).getLock() == Lock.UNLOCKED);
        TheftAlarmState theftAlarmState = (TheftAlarmState) state.getCommand(TheftAlarmState.TYPE);
        assertTrue(theftAlarmState.getState() == TheftAlarmState.State.ARMED);
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