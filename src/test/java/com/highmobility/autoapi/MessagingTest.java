package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class MessagingTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "003701" +
                    "01001001000D48656c6c6f20796f7520746f6f" +
                    "02001101000E2b31203535352d3535352d353535");

    @Test public void send() {
        Command command = CommandResolver.resolve(bytes);
        testState((Messaging.State) command);
    }

    private void testState(Messaging.State state) {
        assertTrue(state.getHandle().getValue().equals("+1 555-555-555"));
        assertTrue(state.getText().getValue().equals("Hello you too"));
        assertTrue(bytesTheSame(state, bytes));
    }

    @Test public void received() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "003701" +
                "01000801000548656c6c6f" +
                "02001101000E2b31203535352d3535352d353535");

        Bytes commandBytes = new Messaging.MessageReceived("Hello", "+1 555-555-555");

        assertTrue(bytesTheSame(commandBytes, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Messaging.MessageReceived command = (Messaging.MessageReceived) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getHandle().getValue().equals("+1 555-555-555"));
        assertTrue(command.getText().getValue().equals("Hello"));
    }

    @Test public void build() {
        Messaging.State.Builder builder = new Messaging.State.Builder();
        builder.setText(new Property("Hello you too"));
        builder.setHandle(new Property("+1 555-555-555"));
        testState(builder.build());
    }
}