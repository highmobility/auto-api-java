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
            "005701" +
                    "010008010005003F5D2F1B" +
                    "01000801000501BF40C49C" +
                    "02000B0100083FC851EB851EB852" +
                    "03000B0100080000000000000000" +
                    "04000B0100083FEF5C28F5C28F5C" +
                    "0500040100010A" +
                    "06000701000441A00000" +
                    "07000701000440D51EB8" +
                    "08000401000103" +
                    "09000401000101" +
                    "0A00050100020101" +
                    "0A00050100020000" +
                    "0B000401000104" +
                    "0C000401000104" +
                    "0D000B0100080000000000000000" +
                    "0E000401000101" +
                    "0F000401000101" +
                    "10000401000101" +
                    "11000401000101" +
                    "12000401000101"
            /* level8 */
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
        assertTrue(state.getUnderSteering() == .19d);
        assertTrue(state.getOverSteering() == 0f);
        assertTrue(state.getGasPedalPosition() == .98d);
        assertTrue(state.getSteeringAngle() == 10);
        assertTrue(state.getBrakePressure() == 20f);
        assertTrue(state.getYawRate() == 6.66f);
        assertTrue(state.getRearSuspensionSteering() == 3);
        assertTrue(state.isEspInterventionActive() == true);

        assertTrue(state.getBrakeTorqueVectoring(Axle.REAR).isActive() == true);
        assertTrue(state.getBrakeTorqueVectoring(Axle.FRONT).isActive() == false);
        assertTrue(state.getGearMode() == GearMode.DRIVE);
        assertTrue(state.getSelectedGear() == 4);
        assertTrue(state.getBrakePedalPosition() == 0d);

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
        builder.setUnderSteering(.19d);
        builder.setOverSteering(0d);
        builder.setGasPedalPosition(.98d);
        builder.setSteeringAngle(10);
        builder.setBrakePressure(20f);
        builder.setYawRate(6.66f);
        builder.setRearSuspensionSteering(3);
        builder.setEspInterventionActive(true);
        builder.addBrakeTorqueVectoring(new BrakeTorqueVectoringProperty(Axle.REAR, true));
        builder.addBrakeTorqueVectoring(new BrakeTorqueVectoringProperty(Axle.FRONT, false));
        builder.setGearMode(GearMode.DRIVE);
        builder.setSelectedGear(4);
        builder.setBrakePedalPosition(0d);

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