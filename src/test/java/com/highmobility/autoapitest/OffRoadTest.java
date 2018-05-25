package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetOffroadState;
import com.highmobility.autoapi.OffroadState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class OffRoadTest {
    @Test
    public void state() {
        Bytes bytes = new Bytes(
                "005201010002000A02000132");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == OffroadState.class);
        OffroadState state = (OffroadState) command;
        assertTrue(state.getRouteIncline() == 10);
        assertTrue(state.getWheelSuspension() == .5f);
    }

    @Test public void get() {
        String waitingForBytes = "005200";
        String commandBytes = ByteUtils.hexFromBytes(new GetOffroadState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("005201");
        Command state = CommandResolver.resolveBytes(bytes);
        assertTrue(((OffroadState)state).getRouteIncline() == null);
    }

    @Test public void build() {
        byte[] bytes = ByteUtils.bytesFromHex("005201010002000A02000132");

        OffroadState.Builder builder = new OffroadState.Builder();
        builder.setRouteIncline(10);
        builder.setWheelSuspension(.5f);
        OffroadState state = builder.build();
        byte[] builtBytes = state.getByteArray();
        assertTrue(Arrays.equals(builtBytes, bytes));
        assertTrue(state.getRouteIncline() == 10);
        assertTrue(state.getWheelSuspension() == .5f);
        assertTrue(state.getType() == OffroadState.TYPE);
    }
}