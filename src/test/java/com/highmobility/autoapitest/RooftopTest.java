package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlRooftop;
import com.highmobility.autoapi.GetRooftopState;
import com.highmobility.autoapi.RooftopState;
import com.highmobility.autoapi.property.ConvertibleRoofState;
import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.autoapi.property.Position;
import com.highmobility.autoapi.property.SunroofTiltState;
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
            "04000102" +
            "05000101");

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
        assertTrue(state.getDimmingPercentage().getValue() == .01f);
        assertTrue(state.getOpenPercentage().getValue() == .53f);
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
        assertTrue(state.getDimmingPercentage().getValue() == 1f);
        assertTrue(state.getOpenPercentage().getValue() == 0f);
    }

    @Test public void get() {
        String waitingForBytes = "002500";
        String commandBytes = ByteUtils.hexFromBytes(new GetRooftopState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void controlRooftop() {

        Bytes waitingForBytes = new Bytes("0025120100010002000100030001000400010105000101");

        Bytes commandBytes = new ControlRooftop(0f, 0f,
                ConvertibleRoofState.Value.CLOSED, SunroofTiltState.Value.TILTED,
                Position.Value.OPEN);

        assertTrue(TestUtils.bytesTheSame(commandBytes, waitingForBytes));

        ControlRooftop command = (ControlRooftop) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getDimmingPercentage() == 0f);
        assertTrue(command.getOpenPercentage() == 0f);
        assertTrue(command.getConvertibleRoofState() == ConvertibleRoofState.Value.CLOSED);
        assertTrue(command.getSunroofTiltState() == SunroofTiltState.Value.TILTED);
        assertTrue(command.getSunroofPosition() == Position.Value.OPEN);

    }

    @Test public void stateBuilder() {
        RooftopState.Builder builder = new RooftopState.Builder();
        builder.setDimmingPercentage(new PercentageProperty(1f));
        builder.setOpenPercentage(new PercentageProperty(0f));
        builder.setConvertibleRoofState(new ConvertibleRoofState(ConvertibleRoofState.Value.OPEN));
        builder.setSunroofTiltState(new SunroofTiltState(SunroofTiltState.Value.HALF_TILTED));
        builder.setSunroofPosition(new Position(Position.Value.OPEN));

        RooftopState state = builder.build();
        testState(state);
    }

    private void testState(RooftopState state) {
        assertTrue(TestUtils.bytesTheSame(state, bytes));
        assertTrue(state.getDimmingPercentage().getValue() == 1f);
        assertTrue(state.getOpenPercentage().getValue() == 0f);
        assertTrue(state.getConvertibleRoofState().getValue() == ConvertibleRoofState.Value.OPEN);
        assertTrue(state.getSunroofTiltState().getValue() == SunroofTiltState.Value.HALF_TILTED);
        assertTrue(state.getSunroofPosition().getValue() == Position.Value.OPEN);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("002501");
        RooftopState state = (RooftopState) CommandResolver.resolve(bytes);
        assertTrue(state.getOpenPercentage().getValue() == null);
    }
}
