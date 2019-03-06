package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetOffroadState;
import com.highmobility.autoapi.OffroadState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class OffRoadTest {
    Bytes bytes = new Bytes(
            "005201" +
                    "010005010002000A" +
                    "02000B0100083FE0000000000000"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == OffroadState.class);
        OffroadState state = (OffroadState) command;
        testState(state);
    }

    private void testState(OffroadState state) {
        assertTrue(state.getRouteIncline().getValue() == 10);
        assertTrue(state.getWheelSuspension().getValue() == .5d);
    }

    @Test public void get() {
        String waitingForBytes = "005200";
        String commandBytes = ByteUtils.hexFromBytes(new GetOffroadState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("005201");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((OffroadState) state).getRouteIncline().getValue() == null);
    }

    @Test public void build() {
        OffroadState.Builder builder = new OffroadState.Builder();
        builder.setRouteIncline(new PropertyInteger(10));
        builder.setWheelSuspension(new Property(.5d));
        OffroadState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));
        testState(state);
    }
}