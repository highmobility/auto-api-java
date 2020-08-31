/*
 * The MIT License
 *
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi.property;

import com.highmobility.autoapi.BaseTest;
import com.highmobility.autoapi.Charging;
import com.highmobility.autoapi.Charging.ChargeMode;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.TestUtils;
import com.highmobility.autoapi.value.Acceleration;
import com.highmobility.autoapi.value.Brand;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PropertyTest extends BaseTest {
    // bytes: 00000160E0EA1388
    Calendar timestamp = TestUtils.getCalendar("2018-01-10T16:32:05+0000");

    @Test
    public void parseValue() throws CommandParseException {
        // assert that bytes are parsed to the value component
        Bytes completeBytes = new Bytes("000004" +
                "01000100"); // value

        Property<ChargeMode> property = new Property(ChargeMode.class, (byte) 0);
        property.update(new Property(completeBytes.getByteArray()));
        assertTrue(property.getValueComponent().getValue() == ChargeMode.IMMEDIATE);
        testValueComponent(property, 1, ChargeMode.IMMEDIATE);
    }

    @Test
    public void parseValueWithTimestamp() throws CommandParseException {
        // assert that bytes are parsed to the value/timestamp component

        Bytes completeBytes = new Bytes("00000F" +
                "01000100" + // value
                "02000800000160E0EA1388"); // timestamp

        Property property = new Property(ChargeMode.class, (byte) 0);
        property.update(new Property(completeBytes.getByteArray()));

        testValueComponent(property, 1, ChargeMode.IMMEDIATE);
        testTimestampComponent(property);

        // parse in different order as well
        completeBytes = new Bytes("00000F" +
                "02000800000160E0EA1388" + // timestamp
                "01000100" // value
        );

        property.update(new Property(completeBytes.getByteArray()));

        testValueComponent(property, 1, ChargeMode.IMMEDIATE);
        testTimestampComponent(property);
    }

    @Test
    public void parseValueWithTimestampLessThanEightBytesLong() throws CommandParseException {
        // assert that bytes are parsed to the value/timestamp component

        Bytes completeBytes = new Bytes("00000F" +
                "01000100" + // value
                "0200060160E0EA1388"); // timestamp 6 bytes

        Property property = new Property(ChargeMode.class, (byte) 0);
        property.update(new Property(completeBytes.getByteArray()));
        testTimestampComponent(property);
    }

    @Test
    public void parseFailure() throws CommandParseException {
        // test bytes correct and components exist
        Bytes completeBytes = new Bytes("00001A" +
                "" + // value
                "02000800000160E0EA1388" + // timestamp
                "03000C000A54727920696e20343073"); //failure

        Property property = new Property(ChargeMode.class, (byte) 0);
        property.update(new Property(completeBytes.getByteArray()));

        testTimestampComponent(property);
        testFailureComponent(property, completeBytes);
        // parse in different order as well

        completeBytes = new Bytes("00001A" +
                "" + // value
                "03000C000A54727920696e20343073" + //failure
                "02000800000160E0EA1388"  // timestamp
        );

        property.update(new Property(completeBytes.getByteArray()));

        testTimestampComponent(property);
        testFailureComponent(property, completeBytes);
    }

    @Test
    public void buildValue() {
        Property property = new Property(ChargeMode.IMMEDIATE);

        int propertyLength = Property.getUnsignedInt(property.getRange(1, 3));
        assertTrue(propertyLength == 4);
        assertTrue(property.getPropertyLength() == 4);

        testValueComponent(property, 1, ChargeMode.IMMEDIATE);
        Bytes completeBytes = new Bytes("00000401000100");
        assertTrue(property.equals(completeBytes));
    }

    @Test
    public void buildValueWithTimestamp() {
        // test bytes correct and components exist
        Property property = new Property((byte) 0x12,
                ChargeMode.IMMEDIATE,
                timestamp,
                null);

        testValueComponent(property, 1, ChargeMode.IMMEDIATE);
        testTimestampComponent(property);

        Bytes completeBytes = new Bytes("12000F" +
                "01000100" + // value
                "02000800000160E0EA1388"); // timestamp
        assertTrue(property.equals(completeBytes));
    }

    private void testValueComponent(Property property, int length, Object value) {
        // test bytes value component identifier and length is correct
        assertTrue(property.getValueComponent() != null);
        assertTrue(property.getValueComponent().getValue() != null);
        assertTrue(property.getValueComponent().getValueBytes().getLength() == length);

        assertTrue(property.getValueComponent().getValue() == value);
    }

    private void testTimestampComponent(Property property) {
        assertTrue(property.getTimestampComponent() != null);
        assertTrue(property.getTimestampComponent().identifier == 0x02);
        assertTrue(TestUtils.dateIsSame(property.getTimestampComponent().getCalendar(), timestamp));
        // test that bytes are set in component
        assertTrue(Property.getLong(property.getTimestampComponent().getValueBytes().getByteArray()) == 1515601925000L);
    }

    private void testFailureComponent(Property property, Bytes expectedBytes) {
        PropertyComponentFailure failureComponent = property.getFailureComponent();
        assertTrue(failureComponent != null);
        assertTrue(failureComponent.identifier == 0x03);
        assertTrue(failureComponent.getFailureDescription().equals("Try in 40s"));
        assertTrue(failureComponent.getFailureReason() == PropertyComponentFailure.Reason.RATE_LIMIT);
        assertTrue(failureComponent.valueBytes.equals("000A54727920696e20343073"));
        assertTrue(property.equals(expectedBytes));
    }

    @Test
    public void testValueComponentFailedParsing() {
        TestUtils.debugLogExpected(() -> {
            // test if float expected but bytes are with smaller length
            // charging with invalid length chargeCurrentAC. cannot parse to float
            // correct chargeCurrentDC
            Charging.State command = (Charging.State) CommandResolver.resolve(
                    COMMAND_HEADER + "002301" +
                            // 19000D01000A0900bfe3333333333333 correct
                            "19000C0100090900bfe33333333333" + // double value length should be 8
                            "0b000401000101");
            assertTrue(command.getBatteryCurrent().getValue() == null);
            assertTrue(command.getBatteryCurrent().getComponent((byte) 0x01).equals(
                    "0100090900bfe33333333333"));
            assertTrue(command.getChargePortState().getValue() != null);
        });
    }

    @Test
    public void testFailureComponentFailedParsing() {
        // test if invalid failure reason, then error logged and failure component not populated, and put to generic
        // component array
        // 0x11 is invalid failure reason
        TestUtils.errorLogExpected(() -> {
            Charging.State command = (Charging.State) CommandResolver.resolve(
                    COMMAND_HEADER + "002301" +
                            "19001C01000A0900bfe3333333333333" + "03000C110A54727920696e20343073" +
                            "0b000401000101");

            assertTrue(command.getBatteryCurrent().getFailureComponent() == null);
            assertTrue(command.getBatteryCurrent().getComponent((byte) 0x03).equals(
                    "03000C110A54727920696e20343073"));
        });
    }

    @Test
    public void buildFailure() {
        // test bytes correct
        Property property = new Property((byte) 0x11,
                null,
                null,
                new PropertyComponentFailure(PropertyComponentFailure.Reason.RATE_LIMIT,
                        "Try in 40s"));

        assertTrue(property.getTimestampComponent() == null);
        assertTrue(property.getValueComponent() == null);
        Bytes completeBytes = new Bytes("11000F" +
                "" + // value
                "" + // timestamp
                "03000C000A54727920696e20343073"); //failure

        testFailureComponent(property, completeBytes);
    }

    @Test
    public void buildFailureIgnoreValue() {
        Property property = new Property((byte) 0x02,
                ChargeMode.IMMEDIATE,
                timestamp,
                new PropertyComponentFailure(PropertyComponentFailure.Reason.RATE_LIMIT, "Try in " +
                        "40s"));

        assertTrue(property.getTimestampComponent() != null);
        assertTrue(property.getValueComponent() == null);
        Bytes completeBytes = new Bytes("02001A" +
                "" + // value
                "02000800000160E0EA1388" + // timestamp
                "03000C000A54727920696e20343073"); //failure
        testFailureComponent(property, completeBytes);
        testTimestampComponent(property);
    }

    @Test
    public void propertyLength() {
        PropertyInteger property = new PropertyInteger((byte) 0x01, false, 2, new Property(2));
        assertTrue(property.equals("0100050100020002"));

        String longString =
                "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring" +
                        "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring" +
                        "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring";

        Property<String> stringProperty = new Property((byte) 0x02, longString);
        assertTrue(stringProperty.getByteArray()[4] == 0x01); // length
        assertTrue(stringProperty.getByteArray()[5] == 0x4A);
    }

    @Test
    public void emptyValueProperty() {
        Bytes bytes = new Bytes("010000"); // data component with 00 length
        new PropertyComponentValue(bytes);
    }

    @Test
    public void emptyString() throws CommandParseException {
        // representing null/"" string
        // null = no value component
        // "" = value component with 0 length
        Property nullStringBytes = new Property(new Bytes("000000").getByteArray());
        Property emptyStringBytes = new Property(new Bytes("000003010000").getByteArray());

        Property<String> nullStringProperty = new Property((byte) 0x00, null);
        assertTrue(nullStringProperty.getValueComponent() == null);
        assertTrue(nullStringProperty.equals(nullStringBytes));

        Property<String> emptyStringProperty = new Property("");
        assertTrue(emptyStringProperty.getValueComponent() != null);
        assertTrue(emptyStringProperty.getValueComponent().getValue() instanceof String);
        assertTrue(emptyStringProperty.getValueComponent().getValue().equals(""));
        assertTrue(emptyStringProperty.equals(emptyStringBytes));

        nullStringProperty = new Property(String.class, (byte) 0);
        nullStringProperty.update(nullStringBytes);
        assertTrue(nullStringProperty.getValueComponent() == null);

        emptyStringProperty = new Property(String.class, (byte) 0);
        emptyStringProperty.update(emptyStringBytes);
        assertTrue(emptyStringProperty.getValueComponent() != null);
        assertTrue(emptyStringProperty.getValueComponent().getValue().equals(""));
    }

    @Test
    public void isUniversalProperty() {
        // isUniversalProperty() is an convenience method in property. Not used internally
        Property<Calendar> timestamp = new Property((byte) 0xA2, new Bytes("41D6F1C07F800000"));
        Property<Bytes> nonce = new Property((byte) 0xA0, new Bytes("324244433743483436"));
        Property<Bytes> sig = new Property((byte) 0xA1, new Bytes(
                "4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6"));


        Property<String> vin = new Property((byte) 0xA3, new Bytes("4a46325348424443374348343531383639")); // JF2SHBDC7CH451869
        Property<Brand> brand = new Property((byte) 0xA4, new Bytes("01"));

        assertTrue(timestamp.isUniversalProperty());
        assertTrue(nonce.isUniversalProperty());
        assertTrue(sig.isUniversalProperty());
        assertTrue(vin.isUniversalProperty());
        assertTrue(brand.isUniversalProperty());
    }

    @Test
    public void integerPropertySignChecked() throws CommandParseException {
        PropertyInteger integerProperty = new PropertyInteger(253);
        integerProperty.update(false, 1, 253);

        assertTrue(integerProperty.getValue() == 253);

        PropertyInteger checked = new PropertyInteger((byte) 0x00, false);
        checked.update(integerProperty);
        // assert that the bytes are correct to create 253 int
        assertTrue(checked.getValue() == 253);
    }

    @Test
    public void integerPropertyUpdateWithoutValue() {
        PropertyComponentFailure failure =
                new PropertyComponentFailure(PropertyComponentFailure.Reason.UNAUTHORISED,
                        "Permissions not granted");
        Property<Integer> property = new Property<Integer>((byte) 0x02, null, null, failure);
        PropertyInteger intProperty = new PropertyInteger(0x02, true, 2, property);

    }
}