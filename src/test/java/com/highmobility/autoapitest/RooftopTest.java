package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlRooftop;
import com.highmobility.autoapi.GetRooftopState;
import com.highmobility.autoapi.RooftopState;
import com.highmobility.autoapi.rooftop.ConvertibleRoofState;
import com.highmobility.autoapi.rooftop.SunroofTiltState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RooftopTest {
    Bytes bytes = new Bytes("002501" +
            "01000164" +
            "02000100" +
            "03000101" +
            "04000102");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.is(RooftopState.TYPE));
        RooftopState state = (RooftopState) command;

        assertTrue(command.getClass() == RooftopState.class);
        assertTrue(state.getDimmingPercentage() == 1f);
        assertTrue(state.getOpenPercentage() == 0f);
        assertTrue(state.getConvertibleRoofState() == ConvertibleRoofState.OPEN);
        assertTrue(state.getSunroofTiltState() == SunroofTiltState.HALF_TILTED);
    }

    @Test
    public void state_random() {
        Bytes bytes = new Bytes(
                "002501" +
                        "01000101" +
                        "02000135");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(RooftopState.TYPE));
        RooftopState state = (RooftopState) command;

        assertTrue(command.getClass() == RooftopState.class);
        assertTrue(state.getDimmingPercentage() == .01f);
        assertTrue(state.getOpenPercentage() == .53f);
    }

    @Test
    public void state_opaque() {
        Bytes bytes = new Bytes(
                "002501" +
                        "01000164" +
                        "02000100");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(RooftopState.TYPE));

        RooftopState state = (RooftopState) command;
        assertTrue(command.getClass() == RooftopState.class);
        assertTrue(state.getDimmingPercentage() == 1f);
        assertTrue(state.getOpenPercentage() == 0f);
    }

    @Test public void get() {
        String waitingForBytes = "002500";
        String commandBytes = ByteUtils.hexFromBytes(new GetRooftopState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void controlRooftop() {
        Bytes waitingForBytes = new Bytes("00251201000100020001000300010004000101");

        Bytes commandBytes = new ControlRooftop(0f, 0f,
                ConvertibleRoofState.CLOSED, SunroofTiltState.TILTED);
        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));

        ControlRooftop command = (ControlRooftop) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getDimmingPercentage() == 0f);
        assertTrue(command.getOpenPercentage() == 0f);
        assertTrue(command.getConvertibleRoofState() == ConvertibleRoofState.CLOSED);
        assertTrue(command.getSunroofTiltState() == SunroofTiltState.TILTED);
    }

    @Test public void stateBuilder() {
        RooftopState.Builder builder = new RooftopState.Builder();
        builder.setDimmingPercentage(1f);
        builder.setOpenPercentage(0f);
        builder.setConvertibleRoofState(ConvertibleRoofState.OPEN);
        builder.setSunroofTiltState(SunroofTiltState.HALF_TILTED);

        RooftopState state = builder.build();
        assertTrue(TestUtils.bytesTheSame(state, bytes));
        assertTrue(state.getDimmingPercentage() == 1f);
        assertTrue(state.getOpenPercentage() == 0f);
        assertTrue(state.getConvertibleRoofState() == ConvertibleRoofState.OPEN);
        assertTrue(state.getSunroofTiltState() == SunroofTiltState.HALF_TILTED);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("002501");
        RooftopState state = (RooftopState) CommandResolver.resolve(bytes);
        assertTrue(state.getOpenPercentage() == null);
    }
}
