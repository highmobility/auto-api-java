package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetOffroadState;
import com.highmobility.autoapi.OffroadState;
import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class OffRoadTest {
    Bytes bytes = new Bytes(
            "005201010002000A02000132");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == OffroadState.class);
        OffroadState state = (OffroadState) command;
        assertTrue(state.getRouteIncline() == 10);
        assertTrue(state.getWheelSuspension().getValue() == .5f);
    }

    @Test public void get() {
        String waitingForBytes = "005200";
        String commandBytes = ByteUtils.hexFromBytes(new GetOffroadState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("005201");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((OffroadState) state).getRouteIncline() == null);
    }

    @Test public void build() {
        OffroadState.Builder builder = new OffroadState.Builder();
        builder.setRouteIncline(10);
        builder.setWheelSuspension(new PercentageProperty(.5f));
        OffroadState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));
        assertTrue(state.getRouteIncline() == 10);
        assertTrue(state.getWheelSuspension().getValue() == .5f);
        assertTrue(state.getType() == OffroadState.TYPE);
    }
}