package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateStartStop;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetStartStopState;
import com.highmobility.autoapi.StartStopState;
import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

public class StartStopTest {
    Bytes bytes = new Bytes(
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
        assertTrue(state.isActive().getValue() == true);
    }

    @Test public void build() {
        StartStopState.Builder builder = new StartStopState.Builder();
        builder.setIsActive(new BooleanProperty(true));
        assertTrue(builder.build().equals(bytes));
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("006300");
        Bytes commandBytes = new GetStartStopState();

        assertTrue(waitingForBytes.equals(commandBytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof GetStartStopState);
    }

    @Test public void activateDeactivate() {
        Bytes waitingForBytes = new Bytes("00631201000100");

        Bytes commandBytes = new ActivateDeactivateStartStop(false);
        assertTrue(TestUtils.bytesTheSame(waitingForBytes, commandBytes));

        ActivateDeactivateStartStop command = (ActivateDeactivateStartStop) CommandResolver
                .resolve(waitingForBytes);
        assertTrue(command.activate() == false);
    }
}
