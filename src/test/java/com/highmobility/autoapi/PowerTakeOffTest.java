package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PowerTakeOffTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "006501" +
                    "01000401000101" +
                    "02000401000101"
    );

    @Test
    public void state() {
        PowerTakeoff.State command = (PowerTakeoff.State) CommandResolver.resolve(bytes);
        testState(command);
    }

    private void testState(PowerTakeoff.State state) {
        assertTrue(state.getStatus().getValue() == ActiveState.ACTIVE);
        assertTrue(state.getEngaged().getValue() == PowerTakeoff.Engaged.ENGAGED);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        PowerTakeoff.State.Builder builder = new PowerTakeoff.State.Builder();
        builder.setStatus(new Property(ActiveState.ACTIVE));
        builder.setEngaged(new Property(PowerTakeoff.Engaged.ENGAGED));
        testState(builder.build());
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "006500";
        String commandBytes = ByteUtils.hexFromBytes(new PowerTakeoff.GetState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void activateDeactivate() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "006501" +
                "01000401000101");
        byte[] commandBytes = new PowerTakeoff.ActivateDeactivatePowerTakeoff(ActiveState.ACTIVE).getByteArray();
        assertTrue(waitingForBytes.equals(commandBytes));
        setRuntime(CommandResolver.RunTime.JAVA);
        PowerTakeoff.ActivateDeactivatePowerTakeoff command = (PowerTakeoff.ActivateDeactivatePowerTakeoff) CommandResolver
                .resolve(waitingForBytes);
        assertTrue(command.getStatus().getValue() == ActiveState.ACTIVE);
    }
}
