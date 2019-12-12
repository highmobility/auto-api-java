package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Acceleration;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.Axle;
import com.highmobility.autoapi.value.BrakeTorqueVectoring;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class RaceTest extends BaseTest {
    Bytes bytes = new Bytes(
            COMMAND_HEADER + "005701" +
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

        assertTrue(command.getClass() == Race.State.class);
        Race.State state = (Race.State) command;
        testState(state);
    }

    private void testState(Race.State state) {
        assertTrue(state.getAcceleration(Acceleration.Direction.LONGITUDINAL).getValue().getGForce() == .864f);
        assertTrue(state.getAcceleration(Acceleration.Direction.LATERAL).getValue().getGForce() == -0.753f);

        assertTrue(state.getUndersteering().getValue() == .19d);
        assertTrue(state.getOversteering().getValue() == 0f);
        assertTrue(state.getGasPedalPosition().getValue() == .98d);
        assertTrue(state.getSteeringAngle().getValue() == 10);
        assertTrue(state.getBrakePressure().getValue() == 20f);
        assertTrue(state.getYawRate().getValue() == 6.66f);
        assertTrue(state.getRearSuspensionSteering().getValue() == 3);
        assertTrue(state.getElectronicStabilityProgram().getValue() == ActiveState.ACTIVE);

        assertTrue(state.getBrakeTorqueVectoring(Axle.REAR).getValue().getActiveState() == ActiveState.ACTIVE);
        assertTrue(state.getBrakeTorqueVectoring(Axle.FRONT).getValue().getActiveState() == ActiveState.INACTIVE);
        assertTrue(state.getGearMode().getValue() == Race.GearMode.DRIVE);

        assertTrue(state.getSelectedGear().getValue() == 4);
        assertTrue(state.getBrakePedalPosition().getValue() == 0d);

        assertTrue(state.getBrakePedalSwitch().getValue() == ActiveState.ACTIVE);
        assertTrue(state.getClutchPedalSwitch().getValue() == ActiveState.ACTIVE);
        assertTrue(state.getAcceleratorPedalIdleSwitch().getValue() == ActiveState.ACTIVE);
        assertTrue(state.getAcceleratorPedalKickdownSwitch().getValue() == ActiveState.ACTIVE);

        assertTrue(state.getVehicleMoving().getValue() == Race.VehicleMoving.MOVING);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        Race.State.Builder builder = new Race.State.Builder();

        builder.addAcceleration(new Property(new Acceleration(Acceleration.Direction.LONGITUDINAL, .864f)));
        builder.addAcceleration(new Property(new Acceleration(Acceleration.Direction.LATERAL, -.753f)));
        builder.setUndersteering(new Property(.19d));
        builder.setOversteering(new Property(0d));
        builder.setGasPedalPosition(new Property(.98d));
        builder.setSteeringAngle(new Property(10));
        builder.setBrakePressure(new Property(20f));
        builder.setYawRate(new Property(6.66f));
        builder.setRearSuspensionSteering(new Property(3));
        builder.setElectronicStabilityProgram(new Property(ActiveState.ACTIVE));
        builder.addBrakeTorqueVectoring(new Property(new BrakeTorqueVectoring(Axle.REAR, ActiveState.ACTIVE)));
        builder.addBrakeTorqueVectoring(new Property(new BrakeTorqueVectoring(Axle.FRONT, ActiveState.INACTIVE)));
        builder.setGearMode(new Property(Race.GearMode.DRIVE));
        builder.setSelectedGear(new Property(4));
        builder.setBrakePedalPosition(new Property(0d));

        builder.setBrakePedalSwitch(new Property(ActiveState.ACTIVE));
        builder.setClutchPedalSwitch(new Property(ActiveState.ACTIVE));
        builder.setAcceleratorPedalIdleSwitch(new Property(ActiveState.ACTIVE));
        builder.setAcceleratorPedalKickdownSwitch(new Property(ActiveState.ACTIVE));
        builder.setVehicleMoving(new Property(Race.VehicleMoving.MOVING));

        Race.State state = builder.build();
        testState(state);
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "005700";
        String commandBytes = ByteUtils.hexFromBytes(new Race.GetState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}