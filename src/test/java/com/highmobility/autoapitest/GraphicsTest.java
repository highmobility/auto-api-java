package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.DisplayImage;
import com.highmobility.utils.ByteUtils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class GraphicsTest {
    @Test public void displayImage() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex
                ("00510001001568747470733a2f2f676f6f2e676c2f567955316970");
        byte[] bytes = new DisplayImage("https://goo.gl/VyU1ip").getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof DisplayImage);
        assertTrue(((DisplayImage)command).getUrl().equals("https://goo.gl/VyU1ip"));
    }
}
