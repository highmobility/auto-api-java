package com.highmobility.autoapi.property;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ValueTest {
    @Test public void testUnsignedIntOverflow() {
        // TODO: 2019-03-07 drivernumber is unsigned. test if there is overflow eg > 128

        int value = 130;
        byte byteValue = Property.intToBytes(value, 1)[0];
        assertTrue(byteValue == (byte)0x82);
        int parsedValue = Property.getUnsignedInt(byteValue);
        assertTrue(value == parsedValue);
    }
}
