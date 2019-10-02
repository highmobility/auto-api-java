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
            "002801" +
                    "01000401000101"
    );

    @Test
    public void state() {
        ValetModeState command = (ValetModeState) CommandResolver.resolve(bytes);
        testState(command);
    }

    private void testState(ValetModeState state) {
        assertTrue(state.getStatus().getValue() == ActiveState.ACTIVE);
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "002800";
        String commandBytes = ByteUtils.hexFromBytes(new GetValetMode().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void activate() {
        Bytes waitingForBytes = new Bytes("002801" +
                "01000401000101");
        Bytes commandBytes = new ActivateDeactivateValetMode(ActiveState.ACTIVE);
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        ActivateDeactivateValetMode command = (ActivateDeactivateValetMode) CommandResolver
                .resolve(waitingForBytes);
        assertTrue(command.getStatus().getValue() == ActiveState.ACTIVE);
    }

    @Test public void builder() {
        ValetModeState.Builder builder = new ValetModeState.Builder();
        builder.setStatus(new Property(ActiveState.ACTIVE));
        testState(builder.build());
    }
}