package com.highmobility.autoapitest;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ParkingBrakeState;
import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PositionProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.PropertyTimestamp;
import com.highmobility.autoapi.property.StringProperty;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;

import static junit.framework.TestCase.assertTrue;

public class PropertyTest {
    Calendar timestamp = TestUtils.getCalendar("2018-01-10T16:32:05+0000");

    public PropertyTest() throws ParseException {
    }

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

    // TODO: 2019-01-09
    // test boolean property ctor with null bytes. Only failure or timestamp

    @Test public void nullBytesOk() {
        byte[] bytes = null;
        Property prop = new Property(bytes);
        assertBaseBytesOk(prop);
        assertTrue(prop.getPropertyIdentifier() == 0x00);
    }

    @Test public void invalidLengthOk() {
        Bytes bytes = new Bytes("0100");
        Property prop = new Property(bytes);
        assertBaseBytesOk(prop);
        assertTrue(prop.getPropertyIdentifier() == 0x01);
    }

    void assertBaseBytesOk(Property prop) {
        assertTrue(prop.getValueLength() == 0);
        assertTrue(prop.getValueByte() == null);
        assertTrue(prop.getValueBytes().length == 0);
    }

    @Test public void timeStampFailureSet() throws ParseException {
        PropertyFailure failure = new PropertyFailure(
                (byte) 0x03,
                PropertyFailure.Reason.EXECUTION_TIMEOUT,
                "ero"
        );

        BooleanProperty property = new BooleanProperty(null, timestamp, failure);
        assertBaseBytesOk(property);
        assertTrue(TestUtils.dateIsSameIgnoreTimezone(property.getTimestamp(), timestamp));
        assertTrue(property.getFailure() == failure);
    }

    @Test
    public void propertyTimestampParsed() throws ParseException {
        String parkingStateProperty = "01000101";
        PropertyTimestamp timestamp = new PropertyTimestamp(new Bytes( "A4000D11010A112200000001" + parkingStateProperty).getByteArray());
        assertTrue(TestUtils.dateIsSame(timestamp.getCalendar(), "2017-01-10T17:34:00+0000"));
        assertTrue(timestamp.getAdditionalData().equals(parkingStateProperty));
    }

    /*@Test public void settingPropertyAfterFailureAddsToFailure() throws CommandParseException {
        PositionProperty p = new PositionProperty();
        p.update(null,
                new PropertyFailure(PropertyFailure.Reason.EXECUTION_TIMEOUT, ""),
                new PropertyTimestamp(timestamp));

        p.update(new Property(new Bytes("02000101")), null, null);
        assertTrue(p.getFailure().getFailedPropertyIdentifier() == 0x02);
        // ^^ this is actually not an use case. In android properties are full.
        // In builder setIdentifier method is used.

        // update and setIdentifier should be protected

        // TODO: 2019-01-11 test for builders that setIdentifier sets the failure and
        //  timestamp identifier.
    }*/
}
