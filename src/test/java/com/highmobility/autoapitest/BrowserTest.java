package com.highmobility.autoapitest;

import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.LoadUrl;
import com.highmobility.utils.ByteUtils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BrowserTest {
    @Test public void loadUrl() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex
                ("00490001001268747470733a2f2f676f6f676c652e636f6d");
        assertTrue(new LoadUrl("https://google.com").equals(waitingForBytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof LoadUrl);

        LoadUrl command = (LoadUrl) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getUrl().equals("https://google.com"));
    }
}
