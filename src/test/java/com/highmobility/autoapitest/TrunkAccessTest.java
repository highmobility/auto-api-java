package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlTrunk;
import com.highmobility.autoapi.GetTrunkState;
import com.highmobility.autoapi.TrunkState;
import com.highmobility.autoapi.property.Position;
import com.highmobility.autoapi.property.value.Lock;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class TrunkAccessTest {
    @Test
    public void state() {
        Bytes bytes = new Bytes("0021010100010002000101");
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == TrunkState.class);
        TrunkState state = (TrunkState) command;
        assertTrue(state.getLockState().getValue() == Lock.UNLOCKED);
        assertTrue(state.getPosition().getValue() == Position.OPEN);
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
                "01000100" +
                "02000101");
        Command commandBytes = new ControlTrunk(Lock.UNLOCKED, Position.OPEN);
        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof ControlTrunk);

        ControlTrunk state = (ControlTrunk) command;
        assertTrue(state.getLock().getValue() == Lock.UNLOCKED);
        assertTrue(state.getPosition().getValue() == Position.OPEN);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("002101");
        TrunkState state = (TrunkState) CommandResolver.resolve(bytes);
        assertTrue(state.getLockState().getValue() == null);
    }
}