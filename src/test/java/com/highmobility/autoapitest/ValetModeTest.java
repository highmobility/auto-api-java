package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateValetMode;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetValetMode;
import com.highmobility.autoapi.ValetMode;
import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

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
        Bytes bytes = new Bytes(
                "00280101000101");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == ValetMode.class);
        ValetMode state = (ValetMode) command;
        assertTrue(state.isActive().getValue() == true);
    }

    @Test public void get() {
        String waitingForBytes = "002800";
        String commandBytes = ByteUtils.hexFromBytes(new GetValetMode().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void activate() {
        Bytes waitingForBytes = new Bytes("00281201000101");
        Bytes commandBytes = new ActivateDeactivateValetMode(true);
        assertTrue(waitingForBytes.equals(commandBytes));

        ActivateDeactivateValetMode command = (ActivateDeactivateValetMode) CommandResolver
                .resolve(waitingForBytes);
        assertTrue(command.activate() == true);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("002801");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((ValetMode) state).isActive() == null);
    }

    @Test public void builder() {
        ValetMode.Builder builder = new ValetMode.Builder();
        builder.setActive(new BooleanProperty(true));
        byte[] bytes = builder.build().getByteArray();
        assertTrue(Arrays.equals(bytes, ByteUtils.bytesFromHex("00280101000101")));
    }
}