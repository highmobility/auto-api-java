package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetRaceState;
import com.highmobility.autoapi.RaceState;
import com.highmobility.autoapi.property.AccelerationProperty;
import com.highmobility.autoapi.property.BrakeTorqueVectoring;
import com.highmobility.autoapi.property.GearMode;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.value.Axle;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

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
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == RaceState.class);
        RaceState state = (RaceState) command;

        assertTrue(state.getAcceleration(
                AccelerationProperty.AccelerationType.LONGITUDINAL).getAcceleration() == .864f);
        assertTrue(state.getAcceleration(
                AccelerationProperty.AccelerationType.LATERAL).getAcceleration() == -0.753f);

        assertTrue(state.getUnderSteering().getValue() == .19d);
        assertTrue(state.getOverSteering().getValue() == 0f);
        assertTrue(state.getGasPedalPosition().getValue() == .98d);
        assertTrue(state.getSteeringAngle().getValue() == 10);
        assertTrue(state.getBrakePressure().getValue() == 20f);
        assertTrue(state.getYawRate().getValue() == 6.66f);
        assertTrue(state.getRearSuspensionSteering().getValue() == 3);
        assertTrue(state.isEspInterventionActive().getValue() == true);

        assertTrue(state.getBrakeTorqueVectoring(Axle.REAR).isActive() == true);
        assertTrue(state.getBrakeTorqueVectoring(Axle.FRONT).isActive() == false);
        assertTrue(state.getGearMode() == GearMode.DRIVE);

        assertTrue(state.getSelectedGear().getValue() == 4);
        assertTrue(state.getBrakePedalPosition().getValue() == 0d);

        assertTrue(state.isBrakePedalSwitchActive().getValue() == true);
        assertTrue(state.isClutchPedalSwitchActive().getValue() == true);
        assertTrue(state.isAcceleratorPedalIdleSwitchActive().getValue() == true);
        assertTrue(state.isAcceleratorPedalKickdownSwitchActive().getValue() == true);

        assertTrue(state.isVehicleMoving().getValue() == true);
    }

    @Test public void build() {
        RaceState.Builder builder = new RaceState.Builder();

        builder.addAccelerationProperty(new AccelerationProperty(AccelerationProperty
                .AccelerationType.LONGITUDINAL, .864f));
        builder.addAccelerationProperty(new AccelerationProperty(AccelerationProperty
                .AccelerationType.LATERAL, -.753f));
        builder.setUnderSteering(new ObjectProperty(.19d));
        builder.setOverSteering(new ObjectProperty(0d));
        builder.setGasPedalPosition(new ObjectProperty(.98d));
        builder.setSteeringAngle(new ObjectPropertyInteger(10));
        builder.setBrakePressure(new ObjectProperty<>(20f));
        builder.setYawRate(new ObjectProperty<>(6.66f));
        builder.setRearSuspensionSteering(new ObjectPropertyInteger(3));
        builder.setEspInterventionActive(new ObjectProperty<>(true));
        builder.addBrakeTorqueVectoring(new BrakeTorqueVectoring(Axle.REAR, true));
        builder.addBrakeTorqueVectoring(new BrakeTorqueVectoring(Axle.FRONT, false));
        builder.setGearMode(GearMode.DRIVE);
        builder.setSelectedGear(new ObjectPropertyInteger(4));
        builder.setBrakePedalPosition(new ObjectProperty(0d));

        builder.setBrakePedalSwitchActive(new ObjectProperty<>(true));
        builder.setClutchPedalSwitchActive(new ObjectProperty<>(true));
        builder.setAcceleratorPedalIdleSwitchActive(new ObjectProperty<>(true));
        builder.setAcceleratorPedalKickdownSwitchActive(new ObjectProperty<>(true));
        builder.setVehicleMoving(new ObjectProperty<>(true));

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