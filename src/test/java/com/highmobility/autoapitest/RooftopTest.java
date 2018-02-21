package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlRooftop;
import com.highmobility.autoapi.GetRooftopState;
import com.highmobility.autoapi.RooftopState;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RooftopTest {
    @Test
    public void state_random() {
        byte[] bytes = Bytes.bytesFromHex(
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
        byte[] bytes = Bytes.bytesFromHex(
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
        String commandBytes = Bytes.hexFromBytes(new GetRooftopState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void controlRooftop() throws CommandParseException {
        String waitingForBytes = "002502" +
                                "0100010A" +
                                "02000100";

        String commandBytes = Bytes.hexFromBytes(new ControlRooftop(.1f, 0f).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}
