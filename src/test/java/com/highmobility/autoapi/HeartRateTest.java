package com.highmobility.autoapi;

import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeartRateTest extends BaseTest {
    @Test public void send() {
        Command actual = new HeartRate.SendHeartRate(70);
        assertTrue(bytesTheSame(actual, new Bytes(COMMAND_HEADER + "002901" +
                "01000401000146")));
    }
}