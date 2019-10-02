package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StartStopTest extends BaseTest {
    Bytes bytes = new Bytes(
            "006301" +
                    "01000401000101"
    );

    @Test
    public void state() {
        EngineStartStopState command = (EngineStartStopState) CommandResolver.resolve(bytes);
        testState(command);
    }

    private void testState(EngineStartStopState state) {
        assertTrue(state.getStatus().getValue() == ActiveState.ACTIVE);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() {
        EngineStartStopState.Builder builder = new EngineStartStopState.Builder();
        builder.setStatus(new Property(ActiveState.ACTIVE));
        testState(builder.build());
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("006300");
        Bytes commandBytes = new GetEngineStartStopState();

        assertTrue(waitingForBytes.equals(commandBytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof GetEngineStartStopState);
    }

    @Test public void activateDeactivate() {
        Bytes waitingForBytes = new Bytes("006301" +
                "01000401000100");

        Bytes commandBytes = new ActivateDeactivateStartStop(ActiveState.INACTIVE);
        assertTrue(bytesTheSame(waitingForBytes, commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        ActivateDeactivateStartStop command = (ActivateDeactivateStartStop) CommandResolver
                .resolve(waitingForBytes);
        assertTrue(command.getStatus().getValue() == ActiveState.INACTIVE);
    }
}
