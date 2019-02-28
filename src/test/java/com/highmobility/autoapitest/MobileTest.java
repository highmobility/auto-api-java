package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetMobileState;
import com.highmobility.autoapi.MobileState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class MobileTest {
    @Test
    public void state() {
        Bytes bytes = new Bytes(
                "006601" +
                        "01000401000101");

        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.is(MobileState.TYPE));
        MobileState state = (MobileState) command;
        assertTrue(state.isConnected().getValue() == true);
    }

    @Test public void get() {
        String waitingForBytes = "006600";
        String commandBytes = ByteUtils.hexFromBytes(new GetMobileState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        /*Bytes bytes = new Bytes("006601");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((MobileState) state).isConnected() == null);*/
    }

    // TBODO:
}