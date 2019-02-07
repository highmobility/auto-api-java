package com.highmobility.autoapitest.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.CommandProperty;
import com.highmobility.autoapi.property.ControlModeProperty;
import com.highmobility.autoapi.property.CoordinatesProperty;
import com.highmobility.autoapi.property.DashboardLight;
import com.highmobility.autoapi.property.DrivingMode;
import com.highmobility.autoapi.property.FlashersStateProperty;
import com.highmobility.autoapi.property.FogLight;
import com.highmobility.autoapi.property.HvacStartingTime;
import com.highmobility.autoapi.property.IntegerArrayProperty;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.KeyFobPositionProperty;
import com.highmobility.autoapi.property.NetworkSecurity;

import com.highmobility.autoapi.property.ObjectPropertyPercentage;
import com.highmobility.autoapi.property.Position;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.SpringRate;
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
import com.highmobility.autoapi.property.homecharger.AuthenticationMechanism;
import com.highmobility.autoapi.property.homecharger.Charging;
import com.highmobility.autoapi.property.homecharger.PriceTariff;
import com.highmobility.autoapi.property.lights.FrontExteriorLightState;
import com.highmobility.autoapi.property.lights.InteriorLamp;
import com.highmobility.autoapi.property.lights.ReadingLamp;
import com.highmobility.autoapi.property.value.Lock;

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

    @Test public void failure() throws CommandParseException {
        Property property = new Property("0C000100");
        assertTrue(new ChargeMode(null, null, failure).getFailure() != null);
    }

    @Test public void chargeMode() throws CommandParseException {
        Property property = new Property("0C000100");
        assertTrue(new ChargeMode(property).getValue() != null);
        ChargeMode updateProp = new ChargeMode((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void chargePortState() throws CommandParseException {
        Property property = new Property("0B000101");

        assertTrue(new ChargePortState(property).getValue() != null);
        ChargePortState updateProp = new ChargePortState((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void chargingState() throws CommandParseException {
        Property property = new Property("17000101");

        assertTrue(new ChargingState(property).getValue() != null);
        ChargingState updateProp = new ChargingState((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void chargingTimer() throws CommandParseException {
        Property property = new Property("1500090212010A1020050000");

        assertTrue(new ChargingTimer(property).getValue() != null);
        ChargingTimer updateProp = new ChargingTimer((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void springRate() throws CommandParseException {
        Property property = new Property("0600020025");
        // TODO: below ones can be tested once for each of the sub value types (object, enum, int)

        // assert ctor parses the value
        assertTrue(new ObjectProperty<>(SpringRate.class, property).getValue() != null);
        // assert update parses the value
        ObjectProperty<SpringRate> updateProp = new ObjectProperty<>(SpringRate.class, (byte) 0x00);
        assertTrue(updateProp.update(property).getValue() != null);
    }

    @Test public void hvacStartingTime() throws CommandParseException {
        Property property = new Property("0B000305121E");

        assertTrue(new HvacStartingTime(property).getValue() != null);
        HvacStartingTime updateProp = new HvacStartingTime((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void controlMode() throws CommandParseException {
        Property property = new Property("01000102");

        assertTrue(new ControlModeProperty(property).getValue() != null);
        ControlModeProperty updateProp = new ControlModeProperty((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void string() throws CommandParseException {
        Property property = new Property("01000100");

        assertTrue(new StringProperty(property).getValue() != null);
        StringProperty updateProp = new StringProperty((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void intArray() throws CommandParseException {
        Property property = new Property("01000100");

        assertTrue(new IntegerArrayProperty(property).getValue() != null);
        IntegerArrayProperty updateProp = new IntegerArrayProperty((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void departureTime() throws CommandParseException {
        Property property = new Property("010003000000");

        assertTrue(new DepartureTime(property).getValue() != null);
        DepartureTime updateProp = new DepartureTime((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void plugType() throws CommandParseException {
        Property property = new Property("01000100");

        assertTrue(new PlugType(property).getValue() != null);
        PlugType updateProp = new PlugType((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void reductionTime() throws CommandParseException {
        Property property = new Property("010003000000");

        assertTrue(new ReductionTime(property).getValue() != null);
        ReductionTime updateProp = new ReductionTime((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void brakeFluidLevel() throws CommandParseException {
        Property property = new Property("01000100");

        assertTrue(new BrakeFluidLevel(property).getValue() != null);
        BrakeFluidLevel updateProp = new BrakeFluidLevel((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void CheckControlMessage() throws CommandParseException {
        Property property = new Property("010009000000000000000000");

        assertTrue(new CheckControlMessage(property).getValue() != null);
        CheckControlMessage updateProp = new CheckControlMessage((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void DiagnosticsTroubleCode() throws CommandParseException {
        Property property = new Property("1D0018020743313633414641064454523231320750454E44494E47");

        assertTrue(new DiagnosticsTroubleCode(property).getValue() != null);
        DiagnosticsTroubleCode updateProp = new DiagnosticsTroubleCode((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void tirePressure() throws CommandParseException {
        Property property = new Property("0100050000000000");

        assertTrue(new TirePressure(property).getValue() != null);
        TirePressure updateProp = new TirePressure((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void tireTemperature() throws CommandParseException {
        Property property = new Property("0100050000000000");

        assertTrue(new TireTemperature(property).getValue() != null);
        TireTemperature updateProp = new TireTemperature((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void washerFluidLevel() throws CommandParseException {
        Property property = new Property("01000100");

        assertTrue(new WasherFluidLevel(property).getValue() != null);
        WasherFluidLevel updateProp = new WasherFluidLevel((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void wheelRpm() throws CommandParseException {
        Property property = new Property("010003000000");

        assertTrue(new WheelRpm(property).getValue() != null);
        WheelRpm updateProp = new WheelRpm((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void drivingMode() throws CommandParseException {
        Property property = new Property("01000100");

        assertTrue(new ObjectProperty<>(DrivingMode.class, property).getValue() != null);

        ObjectProperty<DrivingMode> updateProp = new ObjectProperty<>(DrivingMode.class,
                (byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void integerProperty() throws CommandParseException {
        Property property = new Property("01000100");
        assertTrue(new ObjectPropertyInteger(property, true).getValue() != null);
        ObjectPropertyInteger updateProp = new ObjectPropertyInteger((byte) 0x00, true);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void BooleanProperty() throws CommandParseException {
        Property property = new Property("01000100");

        assertTrue(new ObjectProperty<>(Boolean.class, property).getValue() != null);
        ObjectProperty<Boolean> updateProp = new ObjectProperty<>(Boolean.class, (byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void floatProperty() throws CommandParseException {
        Property property = new Property("01000400000000");

        assertTrue(new ObjectProperty<>(Float.class, property).getValue() != null);
        ObjectProperty<Float> updateProp = new ObjectProperty<>(Float.class, (byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void HvacStartingTime() throws CommandParseException {
        Property property = new Property("010003000000");

        assertTrue(new HvacStartingTime(property).getValue() != null);
        HvacStartingTime updateProp = new HvacStartingTime((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void DashboardLight() throws CommandParseException {
        Property property = new Property("0100020000");

        assertTrue(new DashboardLight(property).getValue() != null);
        DashboardLight updateProp = new DashboardLight((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void ObjectPropertyPercentage() throws CommandParseException {
        Property property = new Property("01000100");

        assertTrue(new ObjectPropertyPercentage(property).getValue() != null);
        ObjectPropertyPercentage updateProp = new ObjectPropertyPercentage((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void FlashersStateProperty() throws CommandParseException {
        Property property = new Property("01000100");

        assertTrue(new FlashersStateProperty(property).getValue() != null);
        FlashersStateProperty updateProp = new FlashersStateProperty((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void Lock() throws CommandParseException {
        Property property = new Property("01000100");

        assertTrue(new Lock(property).getValue() != null);
        Lock updateProp = new Lock((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void Position() throws CommandParseException {
        Property property = new Property("010003000000");

        assertTrue(new Position(property).getValue() != null);
        Position updateProp = new Position((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void CommandProperty() throws CommandParseException {
        Property property = new Property("010013" + "0020010300020000" +
                "A2000812010a1020050000");

        assertTrue(new CommandProperty(property).getValue() != null);
        CommandProperty updateProp = new CommandProperty((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void Charging() throws CommandParseException {
        Property property = new Property("01000100");

        assertTrue(new Charging(property).getValue() != null);
        Charging updateProp = new Charging((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void AuthenticationMechanism() throws CommandParseException {
        Property property = new Property("01000100");

        assertTrue(new AuthenticationMechanism(property).getValue() != null);
        AuthenticationMechanism updateProp = new AuthenticationMechanism((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void NetworkSecurity() throws CommandParseException {
        Property property = new Property("01000100");

        assertTrue(new NetworkSecurity(property).getValue() != null);
        NetworkSecurity updateProp = new NetworkSecurity((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void CoordinatesProperty() throws CommandParseException {
        Property property = new Property("040010404A428F9F44D445402ACF562174C4CE");

        assertTrue(new CoordinatesProperty(property).getValue() != null);
        CoordinatesProperty updateProp = new CoordinatesProperty((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void PriceTariff() throws CommandParseException {
        Property property = new Property("120009004090000003455552");

        assertTrue(new PriceTariff(property).getValue() != null);
        PriceTariff updateProp = new PriceTariff((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void KeyFobPositionProperty() throws CommandParseException {
        Property property = new Property("01000105");

        assertTrue(new KeyFobPositionProperty(property).getValue() != null);
        KeyFobPositionProperty updateProp = new KeyFobPositionProperty((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void FrontExteriorLightState() throws CommandParseException {
        Property property = new Property("01000101");

        assertTrue(new FrontExteriorLightState(property).getValue() != null);
        FrontExteriorLightState updateProp = new FrontExteriorLightState((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void FogLight() throws CommandParseException {
        Property property = new Property("0100020101");

        assertTrue(new FogLight(property).getValue() != null);
        FogLight updateProp = new FogLight((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void ReadingLamp() throws CommandParseException {
        Property property = new Property("0100020101");

        assertTrue(new ReadingLamp(property).getValue() != null);
        ReadingLamp updateProp = new ReadingLamp((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

    @Test public void InteriorLamp() throws CommandParseException {
        Property property = new Property("0100020101");

        assertTrue(new InteriorLamp(property).getValue() != null);
        InteriorLamp updateProp = new InteriorLamp((byte) 0x00);
        updateProp.update(property);
        assertTrue(updateProp.getValue() != null);
    }

}
