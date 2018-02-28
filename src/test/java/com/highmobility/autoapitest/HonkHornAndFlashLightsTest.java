package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateEmergencyFlasher;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.FlashersState;
import com.highmobility.autoapi.GetFlashersState;
import com.highmobility.autoapi.HonkAndFlash;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

public class HonkHornAndFlashLightsTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "00260101000100"
        );

        Command command = null;try {    command = CommandResolver.resolve(bytes);}catch(Exception e) { fail();}

        assertTrue(command.is(FlashersState.TYPE));
        FlashersState state = (FlashersState) command;
        assertTrue(state.getState() == FlashersState.State.INACTIVE);
    }

    @Test public void get() {
        String waitingForBytes = "002600";
        String commandBytes = Bytes.hexFromBytes(new GetFlashersState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void honkAndFlash() {
        String waitingForBytes = "0026020100010002000103";
        String commandBytes = Bytes.hexFromBytes(new HonkAndFlash(0, 3).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test(expected = IllegalArgumentException.class)
    public void honkAndFlashNoArguments() throws IllegalArgumentException {
        new HonkAndFlash(null, null);
    }


    @Test public void activateDeactivate() {
        String waitingForBytes = "00260301";

        String commandBytes = Bytes.hexFromBytes(new ActivateDeactivateEmergencyFlasher(true).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = Bytes.bytesFromHex("002601");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((FlashersState)state).getState() == null);
    }
}
