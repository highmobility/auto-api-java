package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetOffroadState;
import com.highmobility.autoapi.OffroadState;
import com.highmobility.autoapi.property.IntegerProperty;

import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.ObjectPropertyPercentage;
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
        testState((OffroadState) command);
    }

    void testState(OffroadState state) {
        assertTrue(state.getRouteIncline().getValue() == 10);
        assertTrue(state.getWheelSuspension().getValue() == 50);
        assertTrue(state.getType() == OffroadState.TYPE);
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
        builder.setRouteIncline(new IntegerProperty(10));
        builder.setWheelSuspension(new ObjectPropertyPercentage(50));
        OffroadState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));

        testState(state);
    }
}