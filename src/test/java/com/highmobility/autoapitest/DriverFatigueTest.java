package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.DriverFatigueDetected;
import com.highmobility.autoapi.property.FatigueLevel;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DriverFatigueTest {
    @Test
    public void detected() {
        byte[] bytes = Bytes.bytesFromHex(
                "00410101000100");



        Command command = null;try {    command = CommandResolver.resolve(bytes);}catch(Exception e) {    fail();}

        assertTrue(command.is(DriverFatigueDetected.TYPE));
        DriverFatigueDetected state = (DriverFatigueDetected) command;
        assertTrue(state.getFatigueLevel() == FatigueLevel.LIGHT_FATIGUE);
    }
}
