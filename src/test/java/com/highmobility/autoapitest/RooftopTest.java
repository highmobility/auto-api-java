package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlRooftop;
import com.highmobility.autoapi.GetRooftopState;
import com.highmobility.autoapi.RooftopState;
import com.highmobility.autoapi.property.value.Position;
import com.highmobility.autoapi.rooftop.ConvertibleRoofState;
import com.highmobility.autoapi.rooftop.SunroofTiltState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

        assertTrue(command.getClass() == RooftopState.class);
        assertTrue(state.getDimmingPercentage() == 1);
        assertTrue(state.getOpenPercentage() == 0);
        assertTrue(state.getConvertibleRoofState() == ConvertibleRoofState.OPEN);
        assertTrue(state.getSunroofTiltState() == SunroofTiltState.HALF_TILTED);
        assertTrue(state.getSunroofPosition() == Position.OPEN);
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

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(RooftopState.TYPE));
        RooftopState state = (RooftopState) command;

        assertTrue(command.getClass() == RooftopState.class);
        assertTrue(state.getDimmingPercentage() == .01d);
        assertTrue(state.getOpenPercentage() == .53d);
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
                "04000401000101" +
                "05000401000101");

        Bytes commandBytes = new ControlRooftop(0d, 0d,
                ConvertibleRoofState.CLOSED, SunroofTiltState.TILTED, Position.OPEN);

        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));

        ControlRooftop command = (ControlRooftop) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getDimmingPercentage() == 0d);
        assertTrue(command.getOpenPercentage() == 0d);
        assertTrue(command.getConvertibleRoofState() == ConvertibleRoofState.CLOSED);
        assertTrue(command.getSunroofTiltState() == SunroofTiltState.TILTED);

        assertTrue(command.getSunroofPosition() == Position.OPEN);

    }

    @Test public void stateBuilder() {
        RooftopState.Builder builder = new RooftopState.Builder();
        builder.setDimmingPercentage(1d);
        builder.setOpenPercentage(0d);
        builder.setConvertibleRoofState(ConvertibleRoofState.OPEN);
        builder.setSunroofTiltState(SunroofTiltState.HALF_TILTED);
        builder.setSunroofPosition(Position.OPEN);

        RooftopState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));
        assertTrue(state.getDimmingPercentage() == 1f);
        assertTrue(state.getOpenPercentage() == 0f);
        assertTrue(state.getConvertibleRoofState() == ConvertibleRoofState.OPEN);
        assertTrue(state.getSunroofTiltState() == SunroofTiltState.HALF_TILTED);
        assertTrue(state.getSunroofPosition() == Position.OPEN);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("002501");
        RooftopState state = (RooftopState) CommandResolver.resolve(bytes);
        assertTrue(state.getOpenPercentage() == null);
    }
}
