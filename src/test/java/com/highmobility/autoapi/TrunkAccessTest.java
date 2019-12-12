package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.Position;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class TrunkAccessTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "002101" +
                    "01000401000100" +
                    "02000401000101");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.getClass() == Trunk.State.class);
        Trunk.State state = (Trunk.State) command;
        testState(state);
    }

    private void testState(Trunk.State state) {
        assertTrue(state.getLock().getValue() == LockState.UNLOCKED);
        assertTrue(state.getPosition().getValue() == Position.OPEN);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() {
        Trunk.State.Builder builder = new Trunk.State.Builder();
        builder.setLock(new Property(LockState.UNLOCKED));
        builder.setPosition(new Property(Position.OPEN));
        testState(builder.build());
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "002100");
        Bytes commandBytes = new Trunk.GetState();
        assertTrue(waitingForBytes.equals(commandBytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof Trunk.GetState);
    }

    @Test public void control() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "002101" +
                "01000401000100" +
                "02000401000101");
        Command commandBytes = new Trunk.ControlTrunk(LockState.UNLOCKED, Position.OPEN);
        assertTrue(bytesTheSame(commandBytes, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof Trunk.ControlTrunk);
        Trunk.ControlTrunk state = (Trunk.ControlTrunk) command;
        assertTrue(state.getLock().getValue() == LockState.UNLOCKED);
        assertTrue(state.getPosition().getValue() == Position.OPEN);
    }
}