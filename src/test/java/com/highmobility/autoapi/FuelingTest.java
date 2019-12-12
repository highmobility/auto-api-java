package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.LockState;
import com.highmobility.autoapi.value.Position;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FuelingTest extends BaseTest {
    Bytes bytes = new Bytes(COMMAND_HEADER + "004001" +
            "02000401000101" +
            "03000401000100"
    );

    @Test
    public void state() {
        Command state = CommandResolver.resolve(bytes);
        testState((Fueling.State) state);
    }

    private void testState(Fueling.State state) {
        assertTrue(state.getGasFlapLock().getValue() == LockState.LOCKED);
        assertTrue(state.getGasFlapPosition().getValue() == Position.CLOSED);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        Fueling.State.Builder builder = new Fueling.State.Builder();

        builder.setGasFlapLock(new Property(LockState.LOCKED));
        builder.setGasFlapPosition(new Property(Position.CLOSED));

        Fueling.State state = builder.build();
        testState(state);
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex(COMMAND_HEADER + "004000");
        byte[] bytes = new Fueling.GetGasFlapState().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof Fueling.GetGasFlapState);
    }

    @Test public void control() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex(COMMAND_HEADER + "004001" +
                "02000401000100" +
                "03000401000101");

        byte[] bytes = new Fueling.ControlGasFlap(LockState.UNLOCKED, Position.OPEN).getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Fueling.ControlGasFlap openCloseGasFlap =
                (Fueling.ControlGasFlap) CommandResolver.resolve(waitingForBytes);
        assertTrue(Arrays.equals(openCloseGasFlap.getByteArray(), waitingForBytes));
    }
}
