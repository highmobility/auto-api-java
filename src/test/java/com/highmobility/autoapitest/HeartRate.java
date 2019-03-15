package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.SendHeartRate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeartRate {
    @Test public void send() {
        Command actual = new SendHeartRate(70);
        assertTrue(actual.equals("002912" +
                "01000401000146"));
    }
}
