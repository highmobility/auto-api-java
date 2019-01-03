package com.highmobility.autoapitest;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.StringProperty;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;

public class PropertyTest {
    @Test public void propertyLength() {
        IntegerProperty property = new IntegerProperty((byte) 0x01, 2, 2);
        assertTrue(Arrays.equals(property.getByteArray(), new byte[]{0x01, 0x00, 0x02, 0x00,
                0x02}));

        String longString =
                "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring" +
                        "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring" +
                        "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring";

        StringProperty stringProperty = new StringProperty((byte) 0x02, longString);
        assertTrue(stringProperty.getByteArray()[1] == 0x01);
        assertTrue(stringProperty.getByteArray()[2] == 0x4A);
    }

    @Test public void propertyFailure() throws CommandParseException {
        // size 9 + full prop size
        Bytes propertyFailureBytes = new Bytes("A5000D01000A54727920696e20343073");
        PropertyFailure failure = new PropertyFailure(propertyFailureBytes.getByteArray());

        assertTrue(failure.getFailedPropertyIdentifier() == 0x01);
        assertTrue(failure.getFailureReason() == PropertyFailure.Reason.RATE_LIMIT);
        assertTrue(failure.getFailureDescription().equals("Try in 40s"));
        // TBODO:
    }

    @Test public void emptyValueProperty() {
        Bytes bytes = new Bytes("");
        new Property((byte) 0x00, bytes);
    }

    @Test public void nullString() {
        new StringProperty((byte) 0x00, null);
        new StringProperty((byte) 0x00, "");
    }
}
