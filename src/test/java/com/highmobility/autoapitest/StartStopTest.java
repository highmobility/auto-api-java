package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateStartStop;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetStartStopState;
import com.highmobility.autoapi.StartStopState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StartStopTest {
    Bytes bytes = new Bytes(
            "006301" +
                    "01000401000101"
    );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.is(StartStopState.TYPE));
        StartStopState state = (StartStopState) command;
        testState(state);
    }

    private void testState(StartStopState state) {
        assertTrue(state.isActive().getValue() == true);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        StartStopState.Builder builder = new StartStopState.Builder();
        builder.setIsActive(new Property(true));
        testState(builder.build());
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("006300");
        Bytes commandBytes = new GetStartStopState();

        assertTrue(waitingForBytes.equals(commandBytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof GetStartStopState);
    }

    @Test public void activateDeactivate() {
        Bytes waitingForBytes = new Bytes("006312" +
                "01000401000100");

        Bytes commandBytes = new ActivateDeactivateStartStop(false);
        assertTrue(TestUtils.bytesTheSame(waitingForBytes, commandBytes));

        ActivateDeactivateStartStop command = (ActivateDeactivateStartStop) CommandResolver
                .resolve(waitingForBytes);
        assertTrue(command.activate().getValue() == false);
    }

    @Test public void failsWherePropertiesMandatory() {
        TestUtils.errorLogExpected(() -> {
            assertTrue(CommandResolver.resolve(ActivateDeactivateStartStop.TYPE.getIdentifierAndType()).getClass() == Command.class);
        });
    }
}
