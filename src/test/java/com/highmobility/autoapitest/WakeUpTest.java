package com.highmobility.autoapitest;

import com.highmobility.autoapi.WakeUp;
import com.highmobility.utils.ByteUtils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class WakeUpTest {
    @Test public void get() {
        String waitingForBytes = "002202";
        String commandBytes = ByteUtils.hexFromBytes(new WakeUp().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}
