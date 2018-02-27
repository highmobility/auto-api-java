package com.highmobility.autoapitest;

import com.highmobility.autoapi.DisplayImage;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class GraphicsTest {
    @Test public void displayImage() {
        byte[] waitingForBytes = Bytes.bytesFromHex
                ("00510001001568747470733a2f2f676f6f2e676c2f567955316970");
        byte[] bytes = new DisplayImage("https://goo.gl/VyU1ip").getBytes();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
    }
}
