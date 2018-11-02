package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GasFlapState;
import com.highmobility.autoapi.GetGasFlapState;
import com.highmobility.autoapi.OpenCloseGasFlap;
import com.highmobility.autoapi.property.GasFlapStateProperty;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FuelingTest {
    @Test
    public void state() {
        Bytes bytes = new Bytes(
                "00400101000101");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(GasFlapState.TYPE));
        GasFlapState state = (GasFlapState) command;
        assertTrue(state.getState() == GasFlapStateProperty.OPEN);
    }

    @Test public void build() {
        GasFlapState.Builder builder = new GasFlapState.Builder();
        builder.setState(GasFlapStateProperty.OPEN);
        GasFlapState state = builder.build();
        assertTrue(state.equals("00400101000101"));
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004000");
        byte[] bytes = new GetGasFlapState().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof GetGasFlapState);
    }

    @Test public void open() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004012" +
                "01000101");

        byte[] bytes = new OpenCloseGasFlap(GasFlapStateProperty.OPEN).getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));

        OpenCloseGasFlap openCloseGasFlap = (OpenCloseGasFlap) CommandResolver.resolve(waitingForBytes);
        assertTrue(Arrays.equals(openCloseGasFlap.getByteArray(), waitingForBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("004001");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((GasFlapState) state).getState() == null);
    }
}
