package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetRaceState;
import com.highmobility.autoapi.RaceState;
import com.highmobility.autoapi.property.AccelerationProperty;
import com.highmobility.autoapi.property.Axle;
import com.highmobility.autoapi.property.GearMode;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class RaceTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "005701010005003f5d2f1b01000501bf40c49c020001130300010004000162050001E20600044138f5c307000440d51eb808000103090001010A000201010B0001040C0001040D000101");

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.getClass() == RaceState.class);
        RaceState state = (RaceState) command;

        assertTrue(state.getAcceleration(
                AccelerationProperty.AccelerationType.LONGITUDINAL).getAcceleration() == .864f);
        assertTrue(state.getAcceleration(
                AccelerationProperty.AccelerationType.LATERAL).getAcceleration() == -0.753f);
        assertTrue(state.getUndersteering() == .19f);
        assertTrue(state.getOversteering() == 0f);
        assertTrue(state.getGasPedalPosition() == .98f);
        assertTrue(state.getSteeringAngle() == -.3f);
        assertTrue(state.getBrakePressure() == 11.56f);
        assertTrue(state.getYawRate() == 6.66f);
        assertTrue(state.getRearSuspensionSteering() == .03f);
        assertTrue(state.isEspInterventionActive() == true);

        assertTrue(state.getBrakeTorqueVectoring(Axle.REAR).isActive() == true);
        assertTrue(state.getBrakeTorqueVectoring(Axle.FRONT) == null);
        assertTrue(state.getGearMode() == GearMode.DRIVE);
        assertTrue(state.getSelectedGear() == 4);
        assertTrue(state.getBrakePedalPosition() == .01f);
    }

    @Test public void get() {
        String waitingForBytes = "005700";
        String commandBytes = Bytes.hexFromBytes(new GetRaceState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}