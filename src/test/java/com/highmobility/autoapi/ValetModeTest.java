package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class ValetModeTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "002801" +
                    "01000401000101"
    );

    @Test
    public void state() {
        ValetMode.State command = (ValetMode.State) CommandResolver.resolve(bytes);
        testState(command);
    }

    private void testState(ValetMode.State state) {
        assertTrue(state.getStatus().getValue() == ActiveState.ACTIVE);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "002800";
        String commandBytes = ByteUtils.hexFromBytes(new ValetMode.GetValetMode().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void activate() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "002801" +
                "01000401000101");
        Bytes commandBytes = new ValetMode.ActivateDeactivateValetMode(ActiveState.ACTIVE);
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        ValetMode.ActivateDeactivateValetMode command = (ValetMode.ActivateDeactivateValetMode) CommandResolver
                .resolve(waitingForBytes);
        assertTrue(command.getStatus().getValue() == ActiveState.ACTIVE);
    }

    @Test public void builder() {
        ValetMode.State.Builder builder = new ValetMode.State.Builder();
        builder.setStatus(new Property(ActiveState.ACTIVE));
        testState(builder.build());
    }
}