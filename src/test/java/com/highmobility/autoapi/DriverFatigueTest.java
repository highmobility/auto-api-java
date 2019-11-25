package com.highmobility.autoapi;


import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DriverFatigueTest extends BaseTest {
    @Test
    public void detected() {
        Bytes bytes = new Bytes(
                COMMAND_HEADER + "004101" +
                        "01000401000100");

        DriverFatigueState state = (DriverFatigueState) CommandResolver.resolve(bytes);
        assertTrue(state.getDetectedFatigueLevel().getValue() == DriverFatigueState.DetectedFatigueLevel.LIGHT);
    }
}
