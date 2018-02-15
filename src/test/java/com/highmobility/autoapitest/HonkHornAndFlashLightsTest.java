package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateEmergencyFlasher;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.FlashersState;
import com.highmobility.autoapi.GetFlashersState;
import com.highmobility.autoapi.HonkAndFlash;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class HonkHornAndFlashLightsTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "002601000401000102"
        );



        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.is(FlashersState.TYPE));
        FlashersState state = (FlashersState) command;
        assertTrue(state.getState() == FlashersState.State.LEFT_ACTIVE);
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
}
