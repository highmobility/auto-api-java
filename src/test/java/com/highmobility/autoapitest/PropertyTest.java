package com.highmobility.autoapitest;

import com.highmobility.autoapi.ClimateState;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.CapabilityProperty;
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
        assertTrue(property.equals("0100050100020002"));

        String longString =
                "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring" +
                        "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring" +
                        "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring";

        StringProperty stringProperty = new StringProperty((byte) 0x02, longString);
        assertTrue(stringProperty.getPropertyBytes()[4] == 0x01); // length
        assertTrue(stringProperty.getPropertyBytes()[5] == 0x4A);
    }

    @Test public void propertyFailure() throws CommandParseException {
        // size 9 + full prop size
        Bytes propertyFailureBytes = new Bytes("A5001010000D01000A54727920696e20343073");
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


    @Test public void string() {
        Bytes bytes = new Bytes("01001401001131484D3345303733314837373936393543");
        String s = Property.getString(bytes.getByteArray());
    }

    @Test public void capability() {
        Bytes bytes = new Bytes("01000D01000A" +
                "00240001121314151617");
        CapabilityProperty capabilityProperty = new CapabilityProperty(bytes.getByteArray());
        assertTrue(capabilityProperty.isSupported(ClimateState.TYPE));
    }
}
