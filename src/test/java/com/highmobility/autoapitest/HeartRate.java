package com.highmobility.autoapitest;

import com.highmobility.autoapi.SendHeartRate;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class HeartRate {
    @Test public void send() {
        byte[] expecting = Bytes.bytesFromHex("00290246");
        byte[] actual = new SendHeartRate(70).getBytes();
        assertTrue(Arrays.equals(expecting, actual));
    }
}
