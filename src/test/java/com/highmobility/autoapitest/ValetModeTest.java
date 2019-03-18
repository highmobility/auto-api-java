package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateValetMode;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetValetMode;
import com.highmobility.autoapi.ValetMode;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class ValetModeTest {
    Bytes bytes = new Bytes(
            "002801" +
                    "01000401000101"
    );

    @Test
    public void state() {
        ValetMode command = (ValetMode) CommandResolver.resolve(bytes);
        testState(command);
    }

    private void testState(ValetMode state) {
        assertTrue(TestUtils.bytesTheSame(state, bytes));
        assertTrue(state.isActive().getValue() == true);
    }

    @Test public void get() {
        String waitingForBytes = "002800";
        String commandBytes = ByteUtils.hexFromBytes(new GetValetMode().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void activate() {
        Bytes waitingForBytes = new Bytes("002812" +
                "01000401000101");
        Bytes commandBytes = new ActivateDeactivateValetMode(true);
        assertTrue(waitingForBytes.equals(commandBytes));

        ActivateDeactivateValetMode command = (ActivateDeactivateValetMode) CommandResolver
                .resolve(waitingForBytes);
        assertTrue(command.activate().getValue() == true);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("002801");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((ValetMode) state).isActive() == null);
    }

    @Test public void builder() {
        ValetMode.Builder builder = new ValetMode.Builder();
        builder.setActive(new Property(true));
        testState(builder.build());
    }

    @Test public void failsWherePropertiesMandatory() {
        assertTrue(CommandResolver.resolve(ActivateDeactivateValetMode.TYPE.getIdentifierAndType()).getClass() == Command.class);

    }
}