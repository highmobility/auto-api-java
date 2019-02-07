package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetRaceState;
import com.highmobility.autoapi.RaceState;
import com.highmobility.autoapi.property.AccelerationProperty;
import com.highmobility.autoapi.property.BrakeTorqueVectoring;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.GearMode;
import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.ObjectProperty;

import com.highmobility.autoapi.property.ObjectPropertyPercentage;
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
        assertTrue(state.getUnderSteering().getValue() == 19);
        assertTrue(state.getOverSteering().getValue() == 0);
        assertTrue(state.getGasPedalPosition().getValue() == 98);
        assertTrue(state.getSteeringAngle().getValue() == -30);
        assertTrue(state.getBrakePressure().getValue() == 11.56f);
        assertTrue(state.getYawRate().getValue() == 6.66f);
        assertTrue(state.getRearSuspensionSteering().getValue() == 3);
        assertTrue(state.isEspInterventionActive().getValue() == true);

        assertTrue(state.getBrakeTorqueVectoring(Axle.REAR).isActive() == true);
        assertTrue(state.getBrakeTorqueVectoring(Axle.FRONT) == null);
        assertTrue(state.getGearMode() == GearMode.DRIVE);
        assertTrue(state.getSelectedGear().getValue() == 4);
        assertTrue(state.getBrakePedalPosition().getValue() == 1);

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
        builder.setUnderSteering(new ObjectPropertyPercentage(19));
        builder.setOverSteering(new ObjectPropertyPercentage(0));
        builder.setGasPedalPosition(new ObjectPropertyPercentage(98));
        builder.setSteeringAngle(new ObjectPropertyInteger(-30));
        builder.setBrakePressure(new FloatProperty(11.56f));
        builder.setYawRate(new FloatProperty(6.66f));
        builder.setRearSuspensionSteering(new ObjectPropertyInteger(3));
        builder.setEspInterventionActive(new ObjectProperty<>(true));
        builder.addBrakeTorqueVectoring(new BrakeTorqueVectoring(Axle.REAR, true));
        builder.setGearMode(GearMode.DRIVE);
        builder.setSelectedGear(new ObjectPropertyInteger(4));
        builder.setBrakePedalPosition(new ObjectPropertyPercentage(1));

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