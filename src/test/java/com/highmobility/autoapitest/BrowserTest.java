package com.highmobility.autoapitest;

import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.LoadUrl;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BrowserTest {
    @Test public void loadUrl() {
        Bytes waitingForBytes = new Bytes
                ("00490001001268747470733a2f2f676f6f676c652e636f6d");
        LoadUrl command = new LoadUrl("https://google.com");
        assertTrue(TestUtils.bytesTheSame(command, waitingForBytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof LoadUrl);

        command = (LoadUrl) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getUrl().getValue().equals("https://google.com"));
    }
}
