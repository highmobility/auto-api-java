package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlGasFlap;
import com.highmobility.autoapi.GasFlapState;
import com.highmobility.autoapi.GetGasFlapState;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.Position;
import com.highmobility.autoapi.property.value.Lock;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class FuelingTest {
    Bytes bytes = new Bytes("004001" +
            "02000101" +
            "03000100");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.is(GasFlapState.TYPE));
        GasFlapState state = (GasFlapState) command;
        testState(state);
    }

    private void testState(GasFlapState state) {
        assertTrue(state.getLock().getValue() == Lock.LOCKED);
        assertTrue(state.getPosition().getValue() == Position.CLOSED);
    }

    @Test public void build() {
        GasFlapState.Builder builder = new GasFlapState.Builder();

        builder.setLock(new ObjectProperty<>(Lock.LOCKED));
        builder.setPosition(new ObjectProperty<>(Position.CLOSED));

        GasFlapState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));
        testState(state);
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004000");
        byte[] bytes = new GetGasFlapState().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof GetGasFlapState);
    }

    @Test public void control() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004012" +
                "02000100" +
                "03000101");

        byte[] bytes = new ControlGasFlap(Lock.UNLOCKED, Position.OPEN).getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));

        ControlGasFlap openCloseGasFlap =
                (ControlGasFlap) CommandResolver.resolve(waitingForBytes);
        assertTrue(Arrays.equals(openCloseGasFlap.getByteArray(), waitingForBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("004001");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((GasFlapState) state).getLock().getValue() == null);
    }

    @Test public void stateWithTimestamp() {
        Bytes timestampBytes = bytes.concat(new Bytes("A4000911010A112200000002"));
        GasFlapState command = (GasFlapState) CommandResolver.resolve(timestampBytes);
        assertTrue(command.getLock().getTimestamp() != null);
    }
}
