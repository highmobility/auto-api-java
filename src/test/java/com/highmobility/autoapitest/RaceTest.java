package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetRaceState;
import com.highmobility.autoapi.RaceState;
import com.highmobility.autoapi.property.AccelerationProperty;
import com.highmobility.autoapi.property.BrakeTorqueVectoringProperty;
import com.highmobility.autoapi.property.GearMode;
import com.highmobility.autoapi.property.value.Axle;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class RaceTest {
    Bytes bytes = new Bytes(
            "005701010005003f5d2f1b01000501bf40c49c020001130300010004000162050001E20600044138f5c307000440d51eb808000103090001010A000201010B0001040C0001040D000101" +
                    "0E0001010F0001011000010111000101" /*level7*/ +
                    "12000101" /* level8 */
    );

    @Test
    public void state() {

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == RaceState.class);
        RaceState state = (RaceState) command;

        assertTrue(state.getAcceleration(
                AccelerationProperty.AccelerationType.LONGITUDINAL).getAcceleration() == .864f);
        assertTrue(state.getAcceleration(
                AccelerationProperty.AccelerationType.LATERAL).getAcceleration() == -0.753f);
        assertTrue(state.getUnderSteering() == .19f);
        assertTrue(state.getOverSteering() == 0f);
        assertTrue(state.getGasPedalPosition() == .98f);
        assertTrue(state.getSteeringAngle() == -30);
        assertTrue(state.getBrakePressure() == 11.56f);
        assertTrue(state.getYawRate() == 6.66f);
        assertTrue(state.getRearSuspensionSteering() == 3);
        assertTrue(state.isEspInterventionActive() == true);

        assertTrue(state.getBrakeTorqueVectoring(Axle.REAR).isActive() == true);
        assertTrue(state.getBrakeTorqueVectoring(Axle.FRONT) == null);
        assertTrue(state.getGearMode() == GearMode.DRIVE);
        assertTrue(state.getSelectedGear() == 4);
        assertTrue(state.getBrakePedalPosition() == .01f);

        assertTrue(state.isBrakePedalSwitchActive() == true);
        assertTrue(state.isClutchPedalSwitchActive() == true);
        assertTrue(state.isAcceleratorPedalIdleSwitchActive() == true);
        assertTrue(state.isAcceleratorPedalKickdownSwitchActive() == true);

        assertTrue(state.isVehicleMoving() == true);
    }

    @Test public void build() {
        RaceState.Builder builder = new RaceState.Builder();

        builder.addAccelerationProperty(new AccelerationProperty(AccelerationProperty
                .AccelerationType.LONGITUDINAL, .864f));
        builder.addAccelerationProperty(new AccelerationProperty(AccelerationProperty
                .AccelerationType.LATERAL, -.753f));
        builder.setUnderSteering(.19f);
        builder.setOverSteering(0f);
        builder.setGasPedalPosition(.98f);
        builder.setSteeringAngle(-30);
        builder.setBrakePressure(11.56f);
        builder.setYawRate(6.66f);
        builder.setRearSuspensionSteering(3);
        builder.setEspInterventionActive(true);
        builder.addBrakeTorqueVectoring(new BrakeTorqueVectoringProperty(Axle.REAR, true));
        builder.setGearMode(GearMode.DRIVE);
        builder.setSelectedGear(4);
        builder.setBrakePedalPosition(.01f);

        builder.setBrakePedalSwitchActive(true);
        builder.setClutchPedalSwitchActive(true);
        builder.setAcceleratorPedalIdleSwitchActive(true);
        builder.setAcceleratorPedalKickdownSwitchActive(true);
        builder.setVehicleMoving(true);

        RaceState state = builder.build();
        assertTrue(state.equals(bytes));
    }

    @Test public void get() {
        String waitingForBytes = "005700";
        String commandBytes = ByteUtils.hexFromBytes(new GetRaceState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("005701");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((RaceState) state).getGearMode() == null);
    }
}