package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateValetMode;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetValetMode;
import com.highmobility.autoapi.ValetMode;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class ValetModeTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "002801" +
                        "01000101");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == ValetMode.class);
        ValetMode state = (ValetMode) command;
        assertTrue(state.isActive() == true);
    }

    @Test public void get() {
        String waitingForBytes = "002800";
        String commandBytes = Bytes.hexFromBytes(new GetValetMode().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void activate() {
        String waitingForBytes = "00280201";
        String commandBytes = Bytes.hexFromBytes(new ActivateDeactivateValetMode(true).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = Bytes.bytesFromHex("002801");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((ValetMode)state).isActive() == null);
    }
}