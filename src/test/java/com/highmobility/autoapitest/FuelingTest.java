package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GasFlapState;
import com.highmobility.autoapi.GetGasFlapState;
import com.highmobility.autoapi.OpenGasFlap;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FuelingTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "00400101000101");



        Command command = null;try {    command = CommandResolver.resolve(bytes);}catch(Exception e) {    fail();}

        assertTrue(command.is(GasFlapState.TYPE));
        GasFlapState state = (GasFlapState) command;
        assertTrue(state.getState() == com.highmobility.autoapi.property.GasFlapState.OPEN);
    }

    @Test public void get() {
        byte[] waitingForBytes = Bytes.bytesFromHex("004000");
        byte[] bytes = new GetGasFlapState().getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));

        GetGasFlapState get = (GetGasFlapState) CommandResolver.resolve(waitingForBytes);
    }

    @Test public void open() throws CommandParseException {
        byte[] waitingForBytes = Bytes.bytesFromHex("004002");
        byte[] bytes = new OpenGasFlap().getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));

        OpenGasFlap openGasFlap = (OpenGasFlap) CommandResolver.resolve(waitingForBytes);
        assertTrue(Arrays.equals(openGasFlap.getBytes(), waitingForBytes));
    }
}
