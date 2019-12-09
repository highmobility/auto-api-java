package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class OffRoadTest extends BaseTest {
    Bytes bytes = new Bytes(
            "005201" +
                    "010005010002000A" +
                    "02000B0100083FE0000000000000"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        Offroad.State state = (Offroad.State) command;
        testState(state);
    }

    private void testState(Offroad.State state) {
        assertTrue(state.getRouteIncline().getValue() == 10);
        assertTrue(state.getWheelSuspension().getValue() == .5d);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "005200";
        String commandBytes = ByteUtils.hexFromBytes(new Offroad.GetState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void build() {
        Offroad.State.Builder builder = new Offroad.State.Builder();
        builder.setRouteIncline(new Property(10));
        builder.setWheelSuspension(new Property(.5d));
        Offroad.State state = builder.build();
        testState(state);
    }
}