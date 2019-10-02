package com.highmobility.autoapi.v2.property;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValueTest {
    @Test public void testUnsignedIntOverflow() {
        // test if there is overflow eg > 128
        int value = 130;
        byte byteValue = Property.intToBytes(value, 1)[0];
        assertTrue(byteValue == (byte)0x82);
        int parsedValue = Property.getUnsignedInt(byteValue);
        assertTrue(value == parsedValue);
    }
}
