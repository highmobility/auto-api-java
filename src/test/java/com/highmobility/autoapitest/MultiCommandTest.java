package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.LockUnlockDoors;
import com.highmobility.autoapi.MultiCommand;
import com.highmobility.autoapi.SetTheftAlarm;
import com.highmobility.autoapi.TheftAlarmState;
import com.highmobility.autoapi.property.value.Lock;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class MultiCommandTest {
    Bytes bytes = new Bytes(
            "001302" +
                    "01000700201201000101" +
                    "01000700461201000101");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        MultiCommand state = (MultiCommand) command;

        assertTrue(state.getCommands().length == 2);
        assertTrue(((LockUnlockDoors)state.getCommand(LockUnlockDoors.TYPE)).getDoorLock() == Lock.LOCKED);
        assertTrue(((SetTheftAlarm)state.getCommand(SetTheftAlarm.TYPE)).getState() == TheftAlarmState.State.ARMED);
    }

    @Test
    public void build() {
        Command[] commands = new Command[2];
        commands[0] = new LockUnlockDoors(Lock.LOCKED);
        commands[1] = new SetTheftAlarm(TheftAlarmState.State.ARMED);
        MultiCommand command = new MultiCommand(commands);
        assertTrue(TestUtils.bytesTheSame(command, bytes));
    }
}