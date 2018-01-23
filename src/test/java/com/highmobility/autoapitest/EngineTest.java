package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetIgnitionState;
import com.highmobility.autoapi.IgnitionState;
import com.highmobility.autoapi.TurnEngineOnOff;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class EngineTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "00350101000101");

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.is(IgnitionState.TYPE));
        IgnitionState state = (IgnitionState) command;
        assertTrue(state.isOn() == true);
    }

    @Test public void get() {
        String waitingForBytes = "003500";
        String commandBytes = Bytes.hexFromBytes(new GetIgnitionState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void set() {
        String waitingForBytes = "00350201";
        String commandBytes = Bytes.hexFromBytes(new TurnEngineOnOff(true).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}