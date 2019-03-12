package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.DriverFatigueDetected;
import com.highmobility.autoapi.value.FatigueLevel;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DriverFatigueTest {
    @Test
    public void detected() {
        Bytes bytes = new Bytes(
                "00410101000401000100");

        Command command = null;try {    command = CommandResolver.resolve(bytes);}catch(Exception e) {    fail();}

        assertTrue(command.is(DriverFatigueDetected.TYPE));
        DriverFatigueDetected state = (DriverFatigueDetected) command;
        assertTrue(state.getFatigueLevel().getValue() == FatigueLevel.LIGHT_FATIGUE);
    }
}
