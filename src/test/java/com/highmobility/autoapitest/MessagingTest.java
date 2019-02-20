package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.MessageReceived;
import com.highmobility.autoapi.SendMessage;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class MessagingTest {
        Bytes bytes = new Bytes(
                "003701" +
                        "01001101000e2b31203535352d3535352d353535" +
                        "02001001000d48656c6c6f20796f7520746f6f");
    @Test
    public void send() {

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == SendMessage.class);
        SendMessage state = (SendMessage) command;
        assertTrue(state.getRecipientHandle().equals("+1 555-555-555"));
        assertTrue(state.getMessage().equals("Hello you too"));
    }

    @Test public void received() {
        Bytes waitingForBytes = new Bytes("003700" +
                "01001101000e2b31203535352d3535352d353535" +
                "02000801000548656c6c6f");

        byte[] commandBytes = null;
        commandBytes = new MessageReceived("+1 555-555-555", "Hello").getByteArray();

        assertTrue(waitingForBytes.equals(commandBytes));

        MessageReceived command = (MessageReceived) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getSenderHandle().equals("+1 555-555-555"));
        assertTrue(command.getMessage().equals("Hello"));
    }

    @Test public void build() {
        SendMessage.Builder builder = new SendMessage.Builder();
        builder.setRecipientHandle("+1 555-555-555");
        builder.setMessage("Hello you too");
        assertTrue(TestUtils.bytesTheSame(builder.build(), bytes));
    }
}