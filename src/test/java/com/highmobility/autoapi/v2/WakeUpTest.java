package com.highmobility.autoapi.v2;

import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class WakeUpTest extends BaseTest{
    @Test public void get() {
        Bytes waitingForBytes = new Bytes("00220101000401000100");
        Command commandBytes = new WakeUp();
        assertTrue(bytesTheSame(waitingForBytes, commandBytes));
    }
}
