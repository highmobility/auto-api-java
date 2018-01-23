package com.highmobility.autoapitest;

import com.highmobility.autoapi.WakeUp;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class WakeUpTest {
    @Test public void get() {
        String waitingForBytes = "002202";
        String commandBytes = Bytes.hexFromBytes(new WakeUp().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}
