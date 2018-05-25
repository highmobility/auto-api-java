package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetIgnitionState;
import com.highmobility.autoapi.IgnitionState;
import com.highmobility.autoapi.TurnEngineOnOff;
import com.highmobility.utils.ByteUtils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class EngineTest {
    byte[] bytes = ByteUtils.bytesFromHex(
            "0035010100010102000101");
    @Test
    public void state() {
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(IgnitionState.TYPE));
        IgnitionState state = (IgnitionState) command;
        assertTrue(state.isOn() == true);
        assertTrue(state.isAccessoriesIgnitionOn() == true);
    }

    @Test public void get() {
        String waitingForBytes = "003500";
        String commandBytes = ByteUtils.hexFromBytes(new GetIgnitionState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void set() {
        String waitingForBytes = "00350201";
        String commandBytes = ByteUtils.hexFromBytes(new TurnEngineOnOff(true).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        TurnEngineOnOff incoming = (TurnEngineOnOff) CommandResolver.resolve(ByteUtils.bytesFromHex
                (waitingForBytes));
        assertTrue(incoming.isOn() == true);
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("003501");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((IgnitionState) state).isOn() == null);
    }

    @Test public void build() {
        IgnitionState.Builder builder = new IgnitionState.Builder();
        builder.setIsOn(true);
        builder.setAccessoriesIgnition(true);

        IgnitionState state = builder.build();
        byte[] builtBytes = state.getByteArray();
        assertTrue(Arrays.equals(builtBytes, bytes));
        assertTrue(state.isOn() == true);
        assertTrue(state.isAccessoriesIgnitionOn() == true);
        assertTrue(state.getType() == IgnitionState.TYPE);
    }
}