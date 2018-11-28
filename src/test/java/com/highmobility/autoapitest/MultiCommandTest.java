package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetKeyfobPosition;
import com.highmobility.autoapi.KeyfobPosition;
import com.highmobility.autoapi.LockUnlockDoors;
import com.highmobility.autoapi.MultiCommand;
import com.highmobility.autoapi.SetTheftAlarm;
import com.highmobility.autoapi.TheftAlarmState;
import com.highmobility.autoapi.property.doors.DoorLock;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class MultiCommandTest {
    Bytes bytes = new Bytes(
            "001302" +
                    "01000400200201" +
                    "01000400460201");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        MultiCommand state = (MultiCommand) command;

        assertTrue(state.getCommands().length == 2);
        assertTrue(((LockUnlockDoors)state.getCommand(LockUnlockDoors.TYPE)).getDoorLock() == DoorLock.LOCKED);
        assertTrue(((SetTheftAlarm)state.getCommand(SetTheftAlarm.TYPE)).getState() == TheftAlarmState.State.ARMED);
    }

    @Test
    public void build() {
        Command[] commands = new Command[2];
        commands[0] = new LockUnlockDoors(DoorLock.LOCKED);
        commands[1] = new SetTheftAlarm(TheftAlarmState.State.ARMED);
        MultiCommand command = new MultiCommand(commands);
        assertTrue(command.equals(bytes));
    }

    @Test public void get() {
        String waitingForBytes = "004800";
        String commandBytes = ByteUtils.hexFromBytes(new GetKeyfobPosition().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("004801");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((KeyfobPosition) state).getKeyfobPosition() == null);
    }
}