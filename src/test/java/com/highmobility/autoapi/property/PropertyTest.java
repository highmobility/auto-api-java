package com.highmobility.autoapi.property;

import com.highmobility.autoapi.ClimateState;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.value.Capability;
import com.highmobility.autoapi.value.charging.ChargeMode;
import com.highmobility.autoapitest.TestUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import java.util.Calendar;

import static junit.framework.TestCase.assertTrue;

public class PropertyTest {
    // bytes: 00000160E0EA1388
    Calendar timestamp = TestUtils.getCalendar("2018-01-10T16:32:05+0000");

    @Test public void parseValue() throws CommandParseException {
        // assert that bytes are parsed to the value component
        Bytes completeBytes = new Bytes("000004" +
                "01000100"); // value

        Property<ChargeMode> property = new Property(ChargeMode.class, (byte) 0);
        property.update(new Property(completeBytes.getByteArray()));
        assertTrue(property.getValueComponent().getValue() == ChargeMode.IMMEDIATE);
        testValueComponent(property, 1, ChargeMode.IMMEDIATE);
    }

    @Test public void parseValueWithTimestamp() throws CommandParseException {
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

    @Test public void parseFailure() throws CommandParseException {
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

    @Test public void buildValue() {
        Property property = new Property(ChargeMode.IMMEDIATE);

        int propertyLength = Property.getUnsignedInt(property.getRange(1, 3));
        assertTrue(propertyLength == 4);
        assertTrue(property.getPropertyLength() == 4);

        testValueComponent(property, 1, ChargeMode.IMMEDIATE);
        Bytes completeBytes = new Bytes("00000401000100");
        assertTrue(property.equals(completeBytes));
    }

    @Test public void buildValueWithTimestamp() {
        // test bytes correct and components exist
        Property property = new Property(
                ChargeMode.IMMEDIATE,
                timestamp,
                null);

        testValueComponent(property, 1, ChargeMode.IMMEDIATE);
        testTimestampComponent(property);

        Bytes completeBytes = new Bytes("00000F" +
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
        assertTrue(property.getTimestampComponent().getValueBytes().equals("00000160E0EA1388"));
    }

    private void testFailureComponent(Property property, Bytes expectedBytes) {
        PropertyComponentFailure failureComponent = property.getFailureComponent();
        assertTrue(failureComponent != null);
        assertTrue(failureComponent.identifier == 0x03);
        assertTrue(failureComponent.getFailureDescription().equals("Try in 40s"));
        assertTrue(failureComponent.getFailureReason() == PropertyComponentFailure.Reason.RATE_LIMIT);
        assertTrue(property.equals(expectedBytes));
    }

    @Test public void buildFailure() {
        // test bytes correct
        Property property = new Property(
                null,
                null,
                new PropertyComponentFailure(PropertyComponentFailure.Reason.RATE_LIMIT,
                        "Try in 40s"));

        assertTrue(property.getTimestampComponent() == null);
        assertTrue(property.getValueComponent() == null);
        Bytes completeBytes = new Bytes("00000F" +
                "" + // value
                "" + // timestamp
                "03000C000A54727920696e20343073"); //failure
        testFailureComponent(property, completeBytes);
    }

    @Test public void buildFailureIgnoreValue() {
        Property property = new Property(
                ChargeMode.IMMEDIATE,
                timestamp,
                new PropertyComponentFailure(PropertyComponentFailure.Reason.RATE_LIMIT, "Try in " +
                        "40s"));

        assertTrue(property.getTimestampComponent() != null);
        assertTrue(property.getValueComponent() == null);
        Bytes completeBytes = new Bytes("00001A" +
                "" + // value
                "02000800000160E0EA1388" + // timestamp
                "03000C000A54727920696e20343073"); //failure
        testFailureComponent(property, completeBytes);
        testTimestampComponent(property);
    }

    @Test public void propertyLength() {
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

    @Test public void emptyValueProperty() {
        Bytes bytes = new Bytes("010000"); // data component with 00 length
        new PropertyComponentValue(bytes);
    }

    @Test public void emptyString() throws CommandParseException {
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

    @Test public void universalProperty() {
        Property timestamp = new Property((byte) 0xA2, new Bytes("41D6F1C07F800000"));
        Property nonce = new Property((byte) 0xA0, new Bytes("324244433743483436"));
        Property sig = new Property((byte) 0xA1, new Bytes(
                "4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6"));
        assertTrue(timestamp.isUniversalProperty());
        assertTrue(nonce.isUniversalProperty());
        assertTrue(sig.isUniversalProperty());
    }

    @Test public void integerPropertySignChecked() throws CommandParseException {
        PropertyInteger integerProperty = new PropertyInteger(253);
        integerProperty.update(false, 1, 253);

        assertTrue(integerProperty.getValue() == 253);

        PropertyInteger checked = new PropertyInteger((byte) 0x00, false);
        checked.update(integerProperty);
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

    @Test public void returnBaseClassIfRequiredPropertyDoesNotExist() {
        // if child class didnt find a property but it expects that at least one exists, return
        // base command

        Bytes expected = new Bytes("002313" + // SetChargeLimit
                "DD000B0100083FECCCCCCCCCCCCD"); // invalid property identifier
        assertTrue(CommandResolver.resolve(expected).getClass() == Command.class);
    }
}
