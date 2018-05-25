package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlRooftop;
import com.highmobility.autoapi.GetRooftopState;
import com.highmobility.autoapi.RooftopState;
import com.highmobility.utils.ByteUtils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RooftopTest {
    @Test
    public void state_random() {
        byte[] bytes = ByteUtils.bytesFromHex(
                "002501" +
                    "01000101" +
                    "02000135");

        Command command = null;try {    command = CommandResolver.resolve(bytes);}catch(Exception e) {    fail();}

        assertTrue(command.is(RooftopState.TYPE));
        RooftopState state = (RooftopState) command;

        assertTrue(command.getClass() == RooftopState.class);
        assertTrue(((RooftopState)command).getDimmingPercentage() == .01f);
        assertTrue(((RooftopState)command).getOpenPercentage() == .53f);
    }

    @Test
    public void state_opaque() {
        byte[] bytes = ByteUtils.bytesFromHex(
                "002501" +
                    "01000164" +
                    "02000100");

        Command command = null;try {    command = CommandResolver.resolve(bytes);}catch(Exception e) {    fail();}

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
        String waitingForBytes = "002502" +
                                "0100010A" +
                                "02000135";

        String commandBytes = ByteUtils.hexFromBytes(new ControlRooftop(.1f, .53f).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        ControlRooftop command = (ControlRooftop) CommandResolver.resolveHex(waitingForBytes);
        assertTrue(command.getDimmingPercentage() == .1f);
        assertTrue(command.getOpenPercentage() == .53f);
    }

    @Test public void stateBuilder() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("0025010100010102000135");
        RooftopState.Builder builder = new RooftopState.Builder();
        builder.setDimmingPercentage(.01f);
        builder.setOpenPercentage(.53f);
        RooftopState state = builder.build();
        byte[] actualBytes = state.getByteArray();
        assertTrue(Arrays.equals(actualBytes, waitingForBytes));
        assertTrue(state.getDimmingPercentage() == .01f);
        assertTrue(state.getOpenPercentage() == .53f);
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("002501");
        RooftopState state = (RooftopState) CommandResolver.resolve(bytes);
        assertTrue(state.getOpenPercentage() == null);
    }
}
