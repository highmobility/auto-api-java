package com.highmobility.autoapitest.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandWithProperties;
import com.highmobility.autoapi.ControlMode;
import com.highmobility.autoapi.property.Coordinates;
import com.highmobility.autoapi.property.DashboardLight;
import com.highmobility.autoapi.property.HvacStartingTime;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyComponentFailure;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.autoapi.property.SpringRate;
import com.highmobility.autoapi.property.charging.ChargeMode;
import com.highmobility.autoapi.property.charging.ChargingTimer;
import com.highmobility.autoapi.property.charging.DepartureTime;
import com.highmobility.autoapi.property.charging.ReductionTime;
import com.highmobility.autoapi.property.diagnostics.CheckControlMessage;
import com.highmobility.autoapi.property.diagnostics.DiagnosticsTroubleCode;
import com.highmobility.autoapi.property.diagnostics.TirePressure;
import com.highmobility.autoapi.property.diagnostics.TireTemperature;
import com.highmobility.autoapi.property.diagnostics.WheelRpm;
import com.highmobility.autoapi.property.lights.FogLight;
import com.highmobility.autoapi.property.lights.InteriorLamp;
import com.highmobility.autoapi.property.lights.ReadingLamp;

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
    }

    // TODO: 2019-03-04 one type should be tested once. eg enum once, double once.

    @Test public void enumValue() throws CommandParseException {
        Property property = new Property("010004010C000100");
        testClass(ChargeMode.class, property);
    }

    void testClass(Class theclass, Property property) throws CommandParseException {
        assertTrue(new Property<>(theclass, property).getValue() != null);
        Property updateProp = new Property<>(theclass, (byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void chargingTimer() throws CommandParseException {
        Property property = new Property("1500090212010A1020050000");
        testClass(ChargingTimer.class, property);
    }

    @Test public void departureTime() throws CommandParseException {
        Property property = new Property("010003000000");
        testClass(DepartureTime.class, property);
    }

    @Test public void reductionTime() throws CommandParseException {
        Property property = new Property("010003000000");
        testClass(ReductionTime.class, property);
    }

    @Test public void springRate() throws CommandParseException {
        Property property = new Property("0600020025");
        // TODO: below ones can be tested once for each of the sub value types (object, enum,
        //  int), that are all parsed in ObjectProperty

        // assert ctor parses the value
        assertTrue(new Property<>(SpringRate.class, property).getValue() != null);
        // assert update parses the value
        Property<SpringRate> updateProp = new Property<>(SpringRate.class, (byte) 0x00);
        assertTrue(updateProp.update(property).getValue() != null);
    }

    @Test public void hvacStartingTime() throws CommandParseException {
        Property property = new Property("0B000305121E");
        testClass(HvacStartingTime.class, property);
    }

    @Test public void controlMode() throws CommandParseException {
        Property property = new Property("01000102");
        testClass(ControlMode.Value.class, property);
    }

    @Test public void string() throws CommandParseException {
        Property property = new Property("01000100");
        testClass(String.class, property);
        /*assertTrue(new Property<String>(property).getValue() != null);
        Property<String> updateProp = new Property(String.class, (byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);*/
    }

    @Test public void intArray() throws CommandParseException {
        Property property = new Property("01000100");
        testClass(int[].class, property);
//        assertTrue(new PropertyIntegerArray(property).getValue() != null);
//        PropertyIntegerArray updateProp = new PropertyIntegerArray((byte) 0x00);
//        updateProp.update(property);
//        assertTrue(updateProp.getValue() != null);
    }

    @Test public void CheckControlMessage() throws CommandParseException {
        Property property = new Property("010009000000000000000000");
        testClass(CheckControlMessage.class, property);
    }

    @Test public void DiagnosticsTroubleCode() throws CommandParseException {
        Property property = new Property("1D0018020743313633414641064454523231320750454E44494E47");
        testClass(DiagnosticsTroubleCode.class, property);
    }

    @Test public void tirePressure() throws CommandParseException {
        Property property = new Property("0100050000000000");
        testClass(TirePressure.class, property);
    }

    @Test public void tireTemperature() throws CommandParseException {
        Property property = new Property("0100050000000000");
        testClass(TireTemperature.class, property);
    }

    @Test public void wheelRpm() throws CommandParseException {
        Property property = new Property("010003000000");
        testClass(WheelRpm.class, property);
    }

    @Test public void integerProperty() throws CommandParseException {
        Property property = new Property("01000100");
        assertTrue(new PropertyInteger(property, true).getValue() != null);
        PropertyInteger updateProp = new PropertyInteger((byte) 0x00, true);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void BooleanProperty() throws CommandParseException {
        Property property = new Property("01000100");

        assertTrue(new Property<>(Boolean.class, property).getValue() != null);
        Property<Boolean> updateProp = new Property<>(Boolean.class, (byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void floatProperty() throws CommandParseException {
        Property property = new Property("01000400000000");

        assertTrue(new Property<>(Float.class, property).getValue() != null);
        Property<Float> updateProp = new Property<>(Float.class, (byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void HvacStartingTime() throws CommandParseException {
        Property property = new Property("010003000000");
        testClass(HvacStartingTime.class, property);
    }

    @Test public void DashboardLight() throws CommandParseException {
        Property property = new Property("0100020000");
        testClass(DashboardLight.class, property);
    }

    @Test public void ObjectPropertyPercentage() throws CommandParseException {
        Property property = new Property("01000B0100083FF0000000000000");

        assertTrue(new Property<>(Double.class, property).getValue() != null);
        Property<Double> updateProp = new Property<>(Double.class, (byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void CommandProperty() throws CommandParseException {
        Property property = new Property("010013" + "0020010300020000" +
                "A2000812010a1020050000");
        testClass(CommandWithProperties.class, property);
//        assertTrue(new Property<CommandWithProperties>(property).getValue() != null);
//        Property<CommandWithProperties> updateProp = new Property<CommandWithProperties>((byte)
//        0x00);
//        updateProp.update(property);
//        assertTrue(updateProp.getValue() != null);
    }

    @Test public void CoordinatesProperty() throws CommandParseException {
        Property property = new Property("040010404A428F9F44D445402ACF562174C4CE");
        testClass(Coordinates.class, property);
    }

    // TODO: 2019-03-01 test property if exists
    /*@Test public void PriceTariff() throws CommandParseException {
        Property property = new Property("120009004090000003455552");

        assertTrue(new PriceTariff(property).getValue() != null);
        PriceTariff updateProp = new PriceTariff((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }*/

    @Test public void FogLight() throws CommandParseException {
        Property property = new Property("0100020101");
        testClass(FogLight.class, property);
    }

    @Test public void ReadingLamp() throws CommandParseException {
        Property property = new Property("0100020101");
        testClass(ReadingLamp.class, property);
    }

    @Test public void InteriorLamp() throws CommandParseException {
        Property property = new Property("0100020101");
        testClass(InteriorLamp.class, property);
    }
}
