package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlTrunk;
import com.highmobility.autoapi.GetTrunkState;
import com.highmobility.autoapi.TrunkState;
import com.highmobility.autoapi.property.value.Lock;
import com.highmobility.autoapi.property.value.Position;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class TrunkAccessTest {
    Bytes bytes = new Bytes(
            "002101" +
                    "01000401000100" +
                    "02000401000101");
    @Test
    public void state() {

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }
        if (command == null) fail("init failed");

        assertTrue(command.getClass() == TrunkState.class);
        TrunkState state = (TrunkState) command;
        assertTrue(state.getLockState() == Lock.UNLOCKED);
        assertTrue(state.getPosition() == Position.OPEN);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("002100");
        Bytes commandBytes = new GetTrunkState();
        assertTrue(waitingForBytes.equals(commandBytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof GetTrunkState);
    }

    @Test public void control() {
        Bytes waitingForBytes = new Bytes("002112" +
                "01000401000100" +
                "02000401000101");
        Command commandBytes = new ControlTrunk(Lock.UNLOCKED, Position.OPEN);
        assertTrue(waitingForBytes.equals(commandBytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof ControlTrunk);

        ControlTrunk state = (ControlTrunk) command;
        assertTrue(state.getLock() == Lock.UNLOCKED);
        assertTrue(state.getPosition() == Position.OPEN);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("002101");
        TrunkState state = (TrunkState) CommandResolver.resolve(bytes);
        assertTrue(state.getLockState() == null);
    }
}