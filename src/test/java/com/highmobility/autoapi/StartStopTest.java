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
        EngineStartStop.State command = (EngineStartStop.State) CommandResolver.resolve(bytes);
        testState(command);
    }

    private void testState(EngineStartStop.State state) {
        assertTrue(state.getStatus().getValue() == ActiveState.ACTIVE);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void build() {
        EngineStartStop.State.Builder builder = new EngineStartStop.State.Builder();
        builder.setStatus(new Property(ActiveState.ACTIVE));
        testState(builder.build());
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("006300");
        Bytes commandBytes = new EngineStartStop.GetState();

        assertTrue(waitingForBytes.equals(commandBytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof EngineStartStop.GetState);
    }

    @Test public void activateDeactivate() {
        Bytes waitingForBytes = new Bytes("006301" +
                "01000401000100");

        Bytes commandBytes = new EngineStartStop.ActivateDeactivateStartStop(ActiveState.INACTIVE);
        assertTrue(bytesTheSame(waitingForBytes, commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        EngineStartStop.ActivateDeactivateStartStop command = (EngineStartStop.ActivateDeactivateStartStop) CommandResolver
                .resolve(waitingForBytes);
        assertTrue(command.getStatus().getValue() == ActiveState.INACTIVE);
    }
}
