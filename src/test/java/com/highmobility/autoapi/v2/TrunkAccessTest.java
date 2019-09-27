package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.value.Lock;
import com.highmobility.autoapi.v2.value.LockState;
import com.highmobility.autoapi.v2.value.Position;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class TrunkAccessTest extends BaseTest {
    Bytes bytes = new Bytes(
            "002101" +
                    "01000401000100" +
                    "02000401000101");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.getClass() == TrunkState.class);
        TrunkState state = (TrunkState) command;
        testState(state);
    }

    private void testState(TrunkState state) {
        assertTrue(state.getLock().getValue() == LockState.UNLOCKED);
        assertTrue(state.getPosition().getValue() == Position.OPEN);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() {
        TrunkState.Builder builder = new TrunkState.Builder();
        builder.setLock(new Property(LockState.UNLOCKED));
        builder.setPosition(new Property(Position.OPEN));
        testState(builder.build());
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("002100");
        Bytes commandBytes = new GetTrunkState();
        assertTrue(waitingForBytes.equals(commandBytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof GetTrunkState);
    }

    @Test public void control() {
        Bytes waitingForBytes = new Bytes("002101" +
                "01000401000100" +
                "02000401000101");
        Command commandBytes = new ControlTrunk(LockState.UNLOCKED, Position.OPEN);
        assertTrue(bytesTheSame(commandBytes, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof ControlTrunk);
        ControlTrunk state = (ControlTrunk) command;
        assertTrue(state.getLock().getValue() == LockState.UNLOCKED);
        assertTrue(state.getPosition().getValue() == Position.OPEN);
    }
}