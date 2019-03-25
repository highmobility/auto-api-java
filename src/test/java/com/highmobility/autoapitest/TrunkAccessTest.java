package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlTrunk;
import com.highmobility.autoapi.GetTrunkState;
import com.highmobility.autoapi.TrunkState;
import com.highmobility.autoapi.value.Position;
import com.highmobility.autoapi.value.Lock;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
                "01000401000100" +
                "02000401000101");
        Command commandBytes = new ControlTrunk(Lock.UNLOCKED, Position.OPEN);
        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof ControlTrunk);

        ControlTrunk state = (ControlTrunk) command;
        assertTrue(state.getLock().getValue() == Lock.UNLOCKED);
        assertTrue(state.getPosition().getValue() == Position.OPEN);
    }
}