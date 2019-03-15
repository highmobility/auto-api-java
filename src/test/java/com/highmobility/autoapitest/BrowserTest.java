package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.LoadUrl;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrowserTest {
    @Test public void loadUrl() {
        Bytes waitingForBytes = new Bytes("004900" +
                "01001501001268747470733a2f2f676f6f676c652e636f6d");
        LoadUrl command = new LoadUrl("https://google.com");
        assertTrue(TestUtils.bytesTheSame(command, waitingForBytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof LoadUrl);

        command = (LoadUrl) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getUrl().getValue().equals("https://google.com"));
    }

    @Test public void failsWithNoProperties() {
        Bytes waitingForBytes = new Bytes("004900");
        assertTrue(CommandResolver.resolve(waitingForBytes).getClass() == Command.class);
    }
}
