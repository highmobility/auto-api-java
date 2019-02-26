package com.highmobility.autoapitest;

import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.LoadUrl;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BrowserTest {
    @Test public void loadUrl() {
        // TODO: 2019-02-18 verify when doc updated
        Bytes waitingForBytes = new Bytes
                ("004900"+ "01001501001268747470733a2f2f676f6f676c652e636f6d");

        LoadUrl command = new LoadUrl("https://google.com");
        assertTrue(TestUtils.bytesTheSame(command, waitingForBytes));

        command = (LoadUrl) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getUrl().equals("https://google.com"));
    }
}
