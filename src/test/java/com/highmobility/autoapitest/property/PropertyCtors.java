package com.highmobility.autoapitest.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.ControlModeProperty;
import com.highmobility.autoapi.property.DashboardLight;
import com.highmobility.autoapi.property.DrivingMode;
import com.highmobility.autoapi.property.FlashersStateProperty;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.HvacStartingTime;
import com.highmobility.autoapi.property.IntArrayProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.SpringRateProperty;
import com.highmobility.autoapi.property.StringProperty;
import com.highmobility.autoapi.property.charging.ChargeMode;
import com.highmobility.autoapi.property.charging.ChargePortState;
import com.highmobility.autoapi.property.charging.ChargingState;
import com.highmobility.autoapi.property.charging.ChargingTimer;
import com.highmobility.autoapi.property.charging.DepartureTime;
import com.highmobility.autoapi.property.charging.PlugType;
import com.highmobility.autoapi.property.charging.ReductionTime;
import com.highmobility.autoapi.property.diagnostics.BrakeFluidLevel;
import com.highmobility.autoapi.property.diagnostics.CheckControlMessage;
import com.highmobility.autoapi.property.diagnostics.DiagnosticsTroubleCode;
import com.highmobility.autoapi.property.diagnostics.TirePressure;
import com.highmobility.autoapi.property.diagnostics.TireTemperature;
import com.highmobility.autoapi.property.diagnostics.WasherFluidLevel;
import com.highmobility.autoapi.property.diagnostics.WheelRpm;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

// these are to test that correct builder ctors exist

public class PropertyCtors {
    /*
     * test:
     * correct one created with only failure() - tests that builder exists
     * null value is ok (does not crash)
     * update sets value
     */
    PropertyFailure failure = new PropertyFailure(PropertyFailure.Reason.EXECUTION_TIMEOUT, "ba");

    @Test public void chargeMode() throws CommandParseException {
        Property property = new Property("0C000100");
        assertTrue(new ChargeMode(null, null, failure).getFailure() != null);
        assertTrue(new ChargeMode(property).getValue() != null);
        ChargeMode updateProp = new ChargeMode((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void chargePortState() throws CommandParseException {
        Property property = new Property("0B000101");
        assertTrue(new ChargePortState(null, null, failure).getFailure() != null);
        assertTrue(new ChargePortState(property).getValue() != null);
        ChargePortState updateProp = new ChargePortState((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void chargingState() throws CommandParseException {
        Property property = new Property("17000101");
        assertTrue(new ChargingState(null, null, failure).getFailure() != null);
        assertTrue(new ChargingState(property).getValue() != null);
        ChargingState updateProp = new ChargingState((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void chargingTimer() throws CommandParseException {
        Property property = new Property("1500090212010A1020050000");
        assertTrue(new ChargingTimer(null, null, failure).getFailure() != null);
        assertTrue(new ChargingTimer(property).getValue() != null);
        ChargingTimer updateProp = new ChargingTimer((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void springRate() throws CommandParseException {
        Property property = new Property("0600020025");
        assertTrue(new SpringRateProperty(null, null, failure).getFailure() != null);
        assertTrue(new SpringRateProperty(property).getValue() != null);
        SpringRateProperty updateProp = new SpringRateProperty((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void hvacStartingTime() throws CommandParseException {
        Property property = new Property("0B000305121E");
        assertTrue(new HvacStartingTime(null, null, failure).getFailure() != null);
        assertTrue(new HvacStartingTime(property).getValue() != null);
        HvacStartingTime updateProp = new HvacStartingTime((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }


    @Test public void controlMode() throws CommandParseException {
        Property property = new Property("01000102");
        assertTrue(new ControlModeProperty(null, null, failure).getFailure() != null);
        assertTrue(new ControlModeProperty(property).getValue() != null);
        ControlModeProperty updateProp = new ControlModeProperty((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void string() throws CommandParseException {
        Property property = new Property("01000100");
        assertTrue(new StringProperty(null, null, failure).getFailure() != null);
        assertTrue(new StringProperty(property).getValue() != null);
        StringProperty updateProp = new StringProperty((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void intArray() throws CommandParseException {
        Property property = new Property("01000100");
        assertTrue(new IntArrayProperty(null, null, failure).getFailure() != null);
        assertTrue(new IntArrayProperty(property).getValue() != null);
        IntArrayProperty updateProp = new IntArrayProperty((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void departureTime() throws CommandParseException {
        Property property = new Property("010003000000");
        assertTrue(new DepartureTime(null, null, failure).getFailure() != null);
        assertTrue(new DepartureTime(property).getValue() != null);
        DepartureTime updateProp = new DepartureTime((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void plugType() throws CommandParseException {
        Property property = new Property("01000100");
        assertTrue(new PlugType(null, null, failure).getFailure() != null);
        assertTrue(new PlugType(property).getValue() != null);
        PlugType updateProp = new PlugType((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void reductionTime() throws CommandParseException {
        Property property = new Property("010003000000");
        assertTrue(new ReductionTime(null, null, failure).getFailure() != null);
        assertTrue(new ReductionTime(property).getValue() != null);
        ReductionTime updateProp = new ReductionTime((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void brakeFluidLevel() throws CommandParseException {
        Property property = new Property("01000100");
        assertTrue(new BrakeFluidLevel(null, null, failure).getFailure() != null);
        assertTrue(new BrakeFluidLevel(property).getValue() != null);
        BrakeFluidLevel updateProp = new BrakeFluidLevel((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void CheckControlMessage() throws CommandParseException {
        Property property = new Property("010009000000000000000000");
        assertTrue(new CheckControlMessage(null, null, failure).getFailure() != null);
        assertTrue(new CheckControlMessage(property).getValue() != null);
        CheckControlMessage updateProp = new CheckControlMessage((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void DiagnosticsTroubleCode() throws CommandParseException {
        Property property = new Property("1D0018020743313633414641064454523231320750454E44494E47");
        assertTrue(new DiagnosticsTroubleCode(null, null, failure).getFailure() != null);
        assertTrue(new DiagnosticsTroubleCode(property).getValue() != null);
        DiagnosticsTroubleCode updateProp = new DiagnosticsTroubleCode((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void tirePressure() throws CommandParseException {
        Property property = new Property("0100050000000000");
        assertTrue(new TirePressure(null, null, failure).getFailure() != null);
        assertTrue(new TirePressure(property).getValue() != null);
        TirePressure updateProp = new TirePressure((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void tireTemperature() throws CommandParseException {
        Property property = new Property("0100050000000000");
        assertTrue(new TireTemperature(null, null, failure).getFailure() != null);
        assertTrue(new TireTemperature(property).getValue() != null);
        TireTemperature updateProp = new TireTemperature((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void washerFluidLevel() throws CommandParseException {
        Property property = new Property("01000100");
        assertTrue(new WasherFluidLevel(null, null, failure).getFailure() != null);
        assertTrue(new WasherFluidLevel(property).getValue() != null);
        WasherFluidLevel updateProp = new WasherFluidLevel((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void wheelRpm() throws CommandParseException {
        Property property = new Property("010003000000");
        assertTrue(new WheelRpm(null, null, failure).getFailure() != null);
        assertTrue(new WheelRpm(property).getValue() != null);
        WheelRpm updateProp = new WheelRpm((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void drivingMode() throws CommandParseException {
        Property property = new Property("01000100");
        assertTrue(new DrivingMode(null, null, failure).getFailure() != null);
        assertTrue(new DrivingMode(property).getValue() != null);
        DrivingMode updateProp = new DrivingMode((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void integerProperty() throws CommandParseException {
        Property property = new Property("01000100");
        assertTrue(new IntegerProperty(null, null, failure).getFailure() != null);
        assertTrue(new IntegerProperty(property, true).getValue() != null);
        IntegerProperty updateProp = new IntegerProperty((byte) 0x00, true);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void BooleanProperty() throws CommandParseException {
        Property property = new Property("01000100");
        assertTrue(new BooleanProperty(null, null, failure).getFailure() != null);
        assertTrue(new BooleanProperty(property).getValue() != null);
        BooleanProperty updateProp = new BooleanProperty((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void FloatProperty() throws CommandParseException {
        Property property = new Property("01000400000000");
        assertTrue(new FloatProperty(null, null, failure).getFailure() != null);
        assertTrue(new FloatProperty(property).getValue() != null);
        FloatProperty updateProp = new FloatProperty((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void HvacStartingTime() throws CommandParseException {
        Property property = new Property("010003000000");
        assertTrue(new HvacStartingTime(null, null, failure).getFailure() != null);
        assertTrue(new HvacStartingTime(property).getValue() != null);
        HvacStartingTime updateProp = new HvacStartingTime((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void DashboardLight() throws CommandParseException {
        Property property = new Property("0100020000");
        assertTrue(new DashboardLight(null, null, failure).getFailure() != null);
        assertTrue(new DashboardLight(property).getValue() != null);
        DashboardLight updateProp = new DashboardLight((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void PercentageProperty() throws CommandParseException {
        Property property = new Property("01000100");
        assertTrue(new PercentageProperty(null, null, failure).getFailure() != null);
        assertTrue(new PercentageProperty(property).getValue() != null);
        PercentageProperty updateProp = new PercentageProperty((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void FlashersStateProperty() throws CommandParseException {
        Property property = new Property("01000100");
        assertTrue(new FlashersStateProperty(null, null, failure).getFailure() != null);
        assertTrue(new FlashersStateProperty(property).getValue() != null);
        FlashersStateProperty updateProp = new FlashersStateProperty((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }
}
