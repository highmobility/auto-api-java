package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateEmergencyFlasher;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.FlashersState;
import com.highmobility.autoapi.GetFlashersState;
import com.highmobility.autoapi.HonkAndFlash;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HonkHornAndFlashLightsTest {
    Bytes bytes = new Bytes("002601" +
            "01000401000102"
    );

    @Test
    public void state() {
        FlashersState state = (FlashersState) CommandResolver.resolve(bytes);
        testState(state);
    }

    private void testState(FlashersState state) {
        assertTrue(state.getState().getValue() == FlashersState.Value.LEFT_ACTIVE);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "002600";
        String commandBytes = ByteUtils.hexFromBytes(new GetFlashersState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void honkAndFlash() {
        String waitingForBytes = "002612" +
                "01000401000100" +
                "02000401000103";
        HonkAndFlash command = new HonkAndFlash(0, 3);
        assertTrue(command.equals(waitingForBytes));

        command = (HonkAndFlash) CommandResolver.resolveHex(waitingForBytes);
        assertTrue(command.getSeconds() == 0);
        assertTrue(command.getLightFlashCount() == 3);
    }

    @Test
    public void honkAndFlashNoArguments() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> {
            new HonkAndFlash(null, null);
        });
    }

    @Test public void activateDeactivate() {
        String waitingForBytes = "002613" +
                "01000401000101";

        String commandBytes = ByteUtils.hexFromBytes(new ActivateDeactivateEmergencyFlasher(true)
                .getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        ActivateDeactivateEmergencyFlasher command = (ActivateDeactivateEmergencyFlasher)
                CommandResolver.resolveHex(waitingForBytes);
        assertTrue(command.activate().getValue() == true);
    }

    @Test public void builder() {
        FlashersState.Builder builder = new FlashersState.Builder();
        builder.setState(new Property(FlashersState.Value.LEFT_ACTIVE));
        FlashersState state = builder.build();
        testState(state);
    }

    @Test public void failsWherePropertiesMandatory() {
        assertTrue(CommandResolver.resolve(ActivateDeactivateEmergencyFlasher.TYPE.getIdentifierAndType()).getClass() == Command.class);
    }
}
