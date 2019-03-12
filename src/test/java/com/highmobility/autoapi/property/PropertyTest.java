package com.highmobility.autoapi.property;

import com.highmobility.autoapi.ClimateState;
import com.highmobility.autoapi.CommandParseException;

import com.highmobility.autoapi.value.Capability;
import com.highmobility.autoapitest.TestUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;

import static junit.framework.TestCase.assertTrue;

public class PropertyTest {
    Calendar timestamp = TestUtils.getCalendar("2018-01-10T16:32:05+0000");

    // TODO: 2019-02-28 write component test: if components are ordered timestamp first for
    //  instance. if there is only failure component
    //  write tests for failure, timestamp and data + also for building these (ObjectProperty ctor)

    public PropertyTest() throws ParseException {
    }

    @Test public void propertyLength() {
        IntegerProperty property = new IntegerProperty((byte) 0x01, false, 2, new Property(2));
        assertTrue(property.equals("0100050100020002"));
        String longString =
                "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring" +
                        "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring" +
                        "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring";

        Property<String> stringProperty = new Property((byte) 0x02, longString);
        assertTrue(stringProperty.getByteArray()[4] == 0x01); // length
        assertTrue(stringProperty.getByteArray()[5] == 0x4A);
    }

    @Test public void emptyValueProperty() {
        Bytes bytes = new Bytes("010000"); // data component with 00 length
        new PropertyComponentValue(bytes);
    }

    // TODO: 2019-03-11 figure out how to represent null/"" string for builder.
    //  should add new Property ctor? with null component/component with empty bytes
    /*@Test public void nullString() {
        new Property((byte) 0x00, null);
        new Property((byte) 0x00, "");
    }*/

    // TODO: 2019-01-09
    // test boolean property ctor with null bytes. Only failure or timestamp

    @Test public void nullBytesOk() {
        Property prop = new Property(Double.class, (byte) 0x00);
        assertBaseBytesOk(prop);
        assertTrue(prop.getPropertyIdentifier() == 0x00);
    }

    @Test public void universalProperty() {
        // TODO: 2019-03-04
        Property timestamp = new Property((byte) 0xA2, new Bytes("41D6F1C07F800000"));
        Property nonce = new Property((byte) 0xA0, new Bytes("324244433743483436"));
        Property sig = new Property((byte) 0xA1, new Bytes(
                "4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6"));
        assertTrue(timestamp.isUniversalProperty());
        assertTrue(nonce.isUniversalProperty());
        assertTrue(sig.isUniversalProperty());
    }

    void assertBaseBytesOk(Property prop) {
        assertTrue(prop.getValueComponent() == null);
    }

    @Test public void timeStampFailureSet() throws ParseException {
        PropertyComponentFailure failure = new PropertyComponentFailure(
                (byte) 0x03,
                PropertyComponentFailure.Reason.EXECUTION_TIMEOUT,
                "ero"
        );

        Property<Boolean> property = new Property<>(null, timestamp, failure);
        assertBaseBytesOk(property);
        assertTrue(TestUtils.dateIsSameIgnoreTimezone(property.getTimestamp(), timestamp));
        assertTrue(property.getFailureComponent() == failure);
    }

    /*@Test
    public void propertyTimestampParsed() throws ParseException {
        String parkingStateProperty = "01000101";
        PropertyTimestamp timestamp =
                new PropertyTimestamp(new Bytes("A4000D11010A112200000001" +
                parkingStateProperty).getByteArray());
        assertTrue(TestUtils.dateIsSame(timestamp.getCalendar(), "2017-01-10T17:34:00+0000"));
        assertTrue(timestamp.getAdditionalData().equals(parkingStateProperty));
    }*/

    /*@Test public void settingPropertyAfterFailureAddsToFailure() throws CommandParseException {
        PositionProperty p = new PositionProperty();
        p.update(null,
                new PropertyFailure(PropertyFailure.Reason.EXECUTION_TIMEOUT, ""),
                new PropertyTimestamp(timestamp));

        p.update(new Property(new Bytes("02000101")), null, null);
        assertTrue(p.getFailureComponent().getFailedPropertyIdentifier() == 0x02);
        // ^^ this is actually not an use case. In android properties are full.
        // In builder setIdentifier method is used.

        // update and setIdentifier should be protected

        // TODO: 2019-01-11 test for builders that setIdentifier sets the failure and
        //  timestamp identifier.
    }*/

    @Test public void integerPropertySignChecked() throws CommandParseException {
        IntegerProperty integerProperty = new IntegerProperty(253);
        integerProperty.update((byte) 0x00, false, 1);

        assertTrue(integerProperty.getValue() == 253);

        IntegerProperty checked = new IntegerProperty(integerProperty, false);
        // assert that the bytes are correct to create 253 int
        assertTrue(checked.getValue() == 253);
    }

    @Test public void string() {
        Bytes bytes = new Bytes("01001401001131484D3345303733314837373936393543");
        String s = Property.getString(bytes.getByteArray());
    }

    @Test public void capability() throws CommandParseException {
        Bytes bytes = new Bytes("00240001121314151617");
        Capability capabilityProperty = new Capability();
        capabilityProperty.update(bytes);
        assertTrue(capabilityProperty.isSupported(ClimateState.TYPE));
    }
}
