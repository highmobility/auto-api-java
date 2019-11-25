package com.highmobility.autoapi;

import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrowserTest extends BaseTest {
    @Test public void loadUrl() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "004901" +
                "01001501001268747470733a2f2f676f6f676c652e636f6d");

        LoadUrl command = new LoadUrl("https://google.com");
        assertTrue(TestUtils.bytesTheSame(command, waitingForBytes));

        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof LoadUrl);
        command = (LoadUrl) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getUrl().getValue().equals("https://google.com"));
    }
}
