package com.highmobility.autoapitest;

import com.highmobility.autoapi.ClimateState;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.CapabilityProperty;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.ObjectPropertyString;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailureComponent;
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
        ObjectPropertyInteger property = new ObjectPropertyInteger((byte) 0x01, false, 2, 2);
        assertTrue(property.equals("0100050100020002"));
        String longString =
                "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring" +
                        "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring" +
                        "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring";

        ObjectPropertyString stringProperty = new ObjectPropertyString((byte) 0x02, longString);
        assertTrue(stringProperty.getByteArray()[4] == 0x01); // length
        assertTrue(stringProperty.getByteArray()[5] == 0x4A);
    }

    @Test public void emptyValueProperty() {
        Bytes bytes = new Bytes("");
        new Property((byte) 0x00, bytes.getByteArray());
    }

    @Test public void nullString() {
        new ObjectPropertyString((byte) 0x00, null);
        new ObjectPropertyString((byte) 0x00, "");
    }

    // TODO: 2019-01-09
    // test boolean property ctor with null bytes. Only failure or timestamp

    @Test public void nullBytesOk() {
        ObjectProperty prop = new ObjectProperty(Double.class, (byte) 0x00);
        assertBaseBytesOk(prop);
        assertTrue(prop.getPropertyIdentifier() == 0x00);
    }

    @Test public void universalProperty() {
        // TODO: 2019-03-04
        ObjectProperty timestamp = new ObjectProperty((byte) 0xA2, new Bytes("41D6F1C07F800000"));
        ObjectProperty nonce = new ObjectProperty((byte) 0xA0, new Bytes("324244433743483436"));
        ObjectProperty sig = new ObjectProperty((byte) 0xA1, new Bytes("4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6"));
        assertTrue(timestamp.isUniversalProperty());
        assertTrue(nonce.isUniversalProperty());
        assertTrue(sig.isUniversalProperty());
    }

    @Test public void invalidLengthOk() {
        Bytes bytes = new Bytes("0100");
        ObjectProperty prop = new ObjectProperty(bytes);
        assertBaseBytesOk(prop);
        assertTrue(prop.getPropertyIdentifier() == 0x01);
    }

    void assertBaseBytesOk(ObjectProperty prop) {
        assertTrue(prop.getValueByte() == null);
        assertTrue(prop.getValueBytes() == null);
    }

    @Test public void timeStampFailureSet() throws ParseException {
        PropertyFailureComponent failure = new PropertyFailureComponent(
                (byte) 0x03,
                PropertyFailureComponent.Reason.EXECUTION_TIMEOUT,
                "ero"
        );

        ObjectProperty<Boolean> property = new ObjectProperty<>(null, timestamp, failure);
        assertBaseBytesOk(property);
        assertTrue(TestUtils.dateIsSameIgnoreTimezone(property.getTimestamp(), timestamp));
        assertTrue(property.getFailure() == failure);
    }

    /*@Test
    public void propertyTimestampParsed() throws ParseException {
        String parkingStateProperty = "01000101";
        PropertyTimestamp timestamp =
                new PropertyTimestamp(new Bytes("A4000D11010A112200000001" + parkingStateProperty).getByteArray());
        assertTrue(TestUtils.dateIsSame(timestamp.getCalendar(), "2017-01-10T17:34:00+0000"));
        assertTrue(timestamp.getAdditionalData().equals(parkingStateProperty));
    }*/

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

    @Test public void integerPropertySignChecked() throws CommandParseException {
        ObjectPropertyInteger propertyInteger = new ObjectPropertyInteger(253);
        propertyInteger.update((byte) 0x00, false, 1);

        assertTrue(propertyInteger.getValue() == 253);

        ObjectPropertyInteger checked = new ObjectPropertyInteger(propertyInteger, false);
        // assert that the bytes are correct to create 253 int
        assertTrue(checked.getValue() == 253);
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
