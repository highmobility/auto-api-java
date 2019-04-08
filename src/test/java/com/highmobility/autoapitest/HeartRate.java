package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.SendHeartRate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeartRate {
    @Test public void send() {
        Command actual = new SendHeartRate(70);
        assertTrue(actual.equals("002912" +
                "01000401000146"));
    }

    @Test public void failsWherePropertiesMandatory() {
        TestUtils.errorLogExpected(() -> {
            assertTrue(CommandResolver.resolve(SendHeartRate.TYPE.getIdentifierAndType()).getClass() == Command.class);
        });
    }
}
