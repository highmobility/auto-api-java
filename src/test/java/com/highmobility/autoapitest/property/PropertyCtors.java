package com.highmobility.autoapitest.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.HvacStartingTime;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyFailure;
import com.highmobility.autoapi.property.SpringRateProperty;
import com.highmobility.autoapi.property.charging.ChargeMode;
import com.highmobility.autoapi.property.charging.ChargePortState;
import com.highmobility.autoapi.property.charging.ChargingState;
import com.highmobility.autoapi.property.charging.ChargingTimer;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

// these are to test that correct builder ctors exist

public class PropertyCtors {
    /*
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
}
