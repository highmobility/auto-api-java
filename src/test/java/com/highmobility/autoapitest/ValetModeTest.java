package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateValetMode;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetValetMode;
import com.highmobility.autoapi.ValetMode;
import com.highmobility.utils.ByteUtils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class ValetModeTest {
    @Test
    public void state() {
        byte[] bytes = ByteUtils.bytesFromHex(
                "00280101000101");

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
        String commandBytes = ByteUtils.hexFromBytes(new GetValetMode().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void activate() {
        String waitingForBytes = "00280201";
        String commandBytes = ByteUtils.hexFromBytes(new ActivateDeactivateValetMode(true).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        ActivateDeactivateValetMode command = (ActivateDeactivateValetMode) CommandResolver
                .resolve(ByteUtils.bytesFromHex(waitingForBytes));
        assertTrue(command.activate() == true);
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("002801");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((ValetMode) state).isActive() == null);
    }

    @Test public void builder() {
        ValetMode.Builder builder = new ValetMode.Builder();
        builder.setActive(true);
        byte[] bytes = builder.build().getByteArray();
        assertTrue(Arrays.equals(bytes, ByteUtils.bytesFromHex("00280101000101")));
    }
}