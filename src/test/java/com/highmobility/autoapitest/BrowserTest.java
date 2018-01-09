package com.highmobility.autoapitest;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.LoadUrl;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class BrowserTest {
    @Test public void loadUrl() throws CommandParseException, UnsupportedEncodingException {
        byte[] waitingForBytes = Bytes.bytesFromHex("00490001001268747470733a2f2f676f6f676c652e636f6d");
        byte[] bytes = new LoadUrl("https://google.com").getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }
}
