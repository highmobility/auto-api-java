package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.SendHeartRate;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HeartRate {
    @Test public void send() {
        Command actual = new SendHeartRate(70);
        assertTrue(actual.equals("00290246"));
    }
}
