package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PowerTakeOffTest extends BaseTest {
    Bytes bytes = new Bytes(
            "006501" +
                    "01000401000101" +
                    "02000401000101"
    );

    @Test
    public void state() {
        PowerTakeoffState command = (PowerTakeoffState) CommandResolver.resolve(bytes);
        testState(command);
    }

    private void testState(PowerTakeoffState state) {
        assertTrue(state.getStatus().getValue() == ActiveState.ACTIVE);
        assertTrue(state.getEngaged().getValue() == PowerTakeoffState.Engaged.ENGAGED);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        PowerTakeoffState.Builder builder = new PowerTakeoffState.Builder();
        builder.setStatus(new Property(ActiveState.ACTIVE));
        builder.setEngaged(new Property(PowerTakeoffState.Engaged.ENGAGED));
        testState(builder.build());
    }

    @Test public void get() {
        String waitingForBytes = "006500";
        String commandBytes = ByteUtils.hexFromBytes(new GetPowerTakeoffState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void activateDeactivate() {
        Bytes waitingForBytes = new Bytes("006501" +
                "01000401000101");
        byte[] commandBytes = new ActivateDeactivatePowerTakeoff(ActiveState.ACTIVE).getByteArray();
        assertTrue(waitingForBytes.equals(commandBytes));
        setRuntime(CommandResolver.RunTime.JAVA);
        ActivateDeactivatePowerTakeoff command = (ActivateDeactivatePowerTakeoff) CommandResolver
                .resolve(waitingForBytes);
        assertTrue(command.getStatus().getValue() == ActiveState.ACTIVE);
    }
}
