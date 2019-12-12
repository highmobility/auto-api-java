package com.highmobility.autoapi;

import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrowserTest extends BaseTest {
    @Test public void loadUrl() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "004901" +
                "01001501001268747470733a2f2f676f6f676c652e636f6d");

        Browser.LoadUrl command = new Browser.LoadUrl("https://google.com");
        assertTrue(TestUtils.bytesTheSame(command, waitingForBytes));

        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof Browser.LoadUrl);
        command = (Browser.LoadUrl) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getUrl().getValue().equals("https://google.com"));
    }
}
