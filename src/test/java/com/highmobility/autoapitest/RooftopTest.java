package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlRooftop;
import com.highmobility.autoapi.GetRooftopState;
import com.highmobility.autoapi.RooftopState;
import com.highmobility.autoapi.value.ConvertibleRoofState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Position;
import com.highmobility.autoapi.value.SunroofTiltState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RooftopTest {
    Bytes bytes = new Bytes("002501" +
            "01000B0100083FF0000000000000" +
            "02000B0100080000000000000000" +
            "03000401000101" +
            "04000401000102" +
            "05000401000101"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.is(RooftopState.TYPE));
        RooftopState state = (RooftopState) command;
        testState(state);
    }

    @Test
    public void state_random() {
        Bytes bytes = new Bytes(
                "002501" +
                        "01000B0100083F847AE147AE147B" +
                        "02000B0100083FE0F5C28F5C28F6" +
                        "03000401000101" +
                        "04000401000102" +
                        "05000401000101");

        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.is(RooftopState.TYPE));
        RooftopState state = (RooftopState) command;

        assertTrue(command.getClass() == RooftopState.class);

        assertTrue(state.getDimmingPercentage().getValue() == .01d);
        assertTrue(state.getOpenPercentage().getValue() == .53d);
    }

    @Test public void get() {
        String waitingForBytes = "002500";
        String commandBytes = ByteUtils.hexFromBytes(new GetRooftopState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void controlRooftop() {
        Bytes waitingForBytes = new Bytes("002512" +
                "01000B0100080000000000000000" +
                "02000B0100080000000000000000" +
                "03000401000100" +
                "04000401000102" +
                "05000401000101");

        Bytes commandBytes = new ControlRooftop(0d, 0d,
                ConvertibleRoofState.CLOSED, SunroofTiltState.HALF_TILTED, Position.OPEN);
        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));

        ControlRooftop command = (ControlRooftop) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getDimmingPercentage().getValue() == 0d);
        assertTrue(command.getOpenPercentage().getValue() == 0d);
        assertTrue(command.getConvertibleRoofState().getValue() == ConvertibleRoofState.CLOSED);
        assertTrue(command.getSunroofTiltState().getValue() == SunroofTiltState.HALF_TILTED);
        assertTrue(command.getSunroofPosition().getValue() == Position.OPEN);
    }

    @Test public void stateBuilder() {
        RooftopState.Builder builder = new RooftopState.Builder();

        builder.setDimmingPercentage(new Property(1d));
        builder.setOpenPercentage(new Property(0d));
        builder.setConvertibleRoofState(new Property(ConvertibleRoofState.OPEN));
        builder.setSunroofTiltState(new Property(SunroofTiltState.HALF_TILTED));
        builder.setSunroofPosition(new Property(Position.OPEN));

        RooftopState state = builder.build();
        testState(state);
    }

    private void testState(RooftopState state) {
        assertTrue(TestUtils.bytesTheSame(state, bytes));
        assertTrue(state.getDimmingPercentage().getValue() == 1d);
        assertTrue(state.getOpenPercentage().getValue() == 0d);
        assertTrue(state.getConvertibleRoofState().getValue() == ConvertibleRoofState.OPEN);
        assertTrue(state.getSunroofTiltState().getValue() == SunroofTiltState.HALF_TILTED);
        assertTrue(state.getSunroofPosition().getValue() == Position.OPEN);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("002501");
        RooftopState state = (RooftopState) CommandResolver.resolve(bytes);
        assertTrue(state.getOpenPercentage().getValue() == null);
    }
}
