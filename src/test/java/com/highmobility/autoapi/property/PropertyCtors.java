package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyComponentFailure;
import com.highmobility.autoapi.property.charging.ChargeMode;
import com.highmobility.autoapi.property.diagnostics.TirePressure;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

// these are to test that correct builder ctors exist

public class PropertyCtors {
    /*
     * test:
     * null value is ok (does not crash)
     * update sets value
     */
    PropertyComponentFailure failure = new PropertyComponentFailure((byte) 0x00,
            PropertyComponentFailure.Reason.EXECUTION_TIMEOUT, "ba");

    @Test public void failure() {
        assertTrue(new Property<ChargeMode>(null, null, failure).getFailure() != null);
        // TODO: 2019-03-11 verify bytes are correct

    }

    @Test public void enumValue() throws CommandParseException {
        Property property = new Property(new Property(new Bytes("010004010C000100").getByteArray()));
        testClass(ChargeMode.class, property);
    }

    @Test public void objectValue() throws CommandParseException {
        Property property = new Property(new Bytes("0100080100050000000000").getByteArray());
        testClass(TirePressure.class, property);
    }

    @Test public void systemValue() throws CommandParseException {
        Property property = new Property(new Bytes("01000B0100083FF0000000000000").getByteArray());
        testClass(Double.class, property);
    }

    void testClass(Class theclass, Property property) throws CommandParseException {
        assertTrue(new Property<>(theclass, property).getValue() != null);
        Property updateProp = new Property<>(theclass, (byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }
}
