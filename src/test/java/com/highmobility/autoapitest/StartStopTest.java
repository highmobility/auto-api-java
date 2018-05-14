package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateStartStop;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetStartStopState;
import com.highmobility.autoapi.StartStopState;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

public class StartStopTest {
    byte[] bytes = Bytes.bytesFromHex(
            "00630101000101"
    );

    @Test
    public void state() {
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(StartStopState.TYPE));
        StartStopState state = (StartStopState) command;
        assertTrue(state.isActive() == true);
    }

    @Test public void build() {
        StartStopState.Builder builder = new StartStopState.Builder();
        builder.setIsActive(true);
        assertTrue(Arrays.equals(builder.build().getBytes(), bytes));
    }

    @Test public void get() {
        byte[] waitingForBytes = Bytes.bytesFromHex("006300");
        byte[] commandBytes = new GetStartStopState().getBytes();

        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof GetStartStopState);
    }

    @Test public void activateDeactivate() {
        byte[] waitingForBytes = Bytes.bytesFromHex("00630201000101");

        byte[] commandBytes = new ActivateDeactivateStartStop(true).getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        ActivateDeactivateStartStop command = (ActivateDeactivateStartStop) CommandResolver
                .resolve(waitingForBytes);
        assertTrue(command.activate() == true);
    }
}
