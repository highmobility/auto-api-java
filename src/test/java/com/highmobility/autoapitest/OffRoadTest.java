package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetOffroadState;
import com.highmobility.autoapi.OffRoadState;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class OffRoadTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "005201010002000A02000132");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == OffRoadState.class);
        OffRoadState state = (OffRoadState) command;
        assertTrue(state.getRouteIncline() == 10);
        assertTrue(state.getWheelSuspension() == .5f);
    }

    @Test public void get() {
        String waitingForBytes = "005200";
        String commandBytes = Bytes.hexFromBytes(new GetOffroadState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = Bytes.bytesFromHex("005201");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((OffRoadState)state).getRouteIncline() == null);
    }
}