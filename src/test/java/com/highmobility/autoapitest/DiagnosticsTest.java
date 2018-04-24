package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.DiagnosticsState;
import com.highmobility.autoapi.GetDiagnosticsState;
import com.highmobility.autoapi.property.TireStateProperty;
import com.highmobility.autoapi.property.WasherFluidLevel;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class DiagnosticsTest {
    @Test public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "0033010100030249F00200020063030002003C04000209C40500015A0600020109070004410c000008000440c66666090001010A000B004013d70a4220000002EA0A000B014013d70a4220000002EA0A000B024013d70a4220000002EA0A000B034013d70a4220000002EA0B0004414000000C00043F0000000D000205DC0E0002000A" +
        "0F0004420E0000");



        Command command = null;try {    command = CommandResolver.resolve(bytes);}catch(Exception e) {    fail();}

        assertTrue(command.getClass() == DiagnosticsState.class);
        DiagnosticsState state = (DiagnosticsState)command;

        assertTrue(state.getMileage() == 150000);
        assertTrue(state.getOilTemperature() == 99);
        assertTrue(state.getSpeed() == 60);
        assertTrue(state.getRpm() == 2500);
        assertTrue(state.getRange() == 265);
        assertTrue(state.getFuelLevel() == .9f);
        assertTrue(state.getCurrentFuelConsumption() == 8.75f);
        assertTrue(state.getTripFuelConsumption() == 6.2f);
        assertTrue(state.getWasherFluidLevel() == WasherFluidLevel.FULL);
        assertTrue(state.getFuelVolume() == 35.5f);

        assertTrue(state.getTireStates().length == 4);
        boolean leftExists = false, rightExist = false, rearLeftExists = false, rearRightExists = false;

        for (TireStateProperty tireState : state.getTireStates()) {
            switch (tireState.getLocation()) {
                case FRONT_LEFT:
                    leftExists = true;
                    assertTrue(tireState.getPressure() == 2.31f);
                    assertTrue(tireState.getTemperature() == 40f);
                    assertTrue(tireState.getRpm() == 746);
                    break;
                case FRONT_RIGHT:
                    rightExist = true;
                    assertTrue(tireState.getPressure() == 2.31f);
                    assertTrue(tireState.getTemperature() == 40f);
                    assertTrue(tireState.getRpm() == 746);
                    break;
                case REAR_RIGHT:
                    rearRightExists = true;
                    assertTrue(tireState.getPressure() == 2.31f);
                    assertTrue(tireState.getTemperature() == 40f);
                    assertTrue(tireState.getRpm() == 746);
                    break;
                case REAR_LEFT:
                    rearLeftExists = true;
                    assertTrue(tireState.getPressure() == 2.31f);
                    assertTrue(tireState.getTemperature() == 40f);
                    assertTrue(tireState.getRpm() == 746);
                    break;
            }
        }

        assertTrue(leftExists == true);
        assertTrue(rightExist == true);
        assertTrue(rearRightExists == true);
        assertTrue(rearLeftExists == true);

        assertTrue(state.getNonce() == null);
        assertTrue(state.getSignature() == null);

        assertTrue(state.getBatteryVoltage() == 12f);
        assertTrue(state.getAdBlueLevel() == .5f);
        assertTrue(state.getDistanceDrivenSinceReset() == 1500);
        assertTrue(state.getDistanceDrivenSinceEngineStart() == 10);

    }

    @Test public void get() {
        String waitingForBytes = "003300";
        String commandBytes = Bytes.hexFromBytes(new GetDiagnosticsState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));

        Command command = CommandResolver.resolve(Bytes.bytesFromHex(waitingForBytes));
        assertTrue(command instanceof GetDiagnosticsState);
    }

    @Test public void build() {
        DiagnosticsState.Builder builder = new DiagnosticsState.Builder();
        builder.setMileage(150000);
        builder.setOilTemperature(99);
        builder.setSpeed(60);
        builder.setRpm(2500);
        builder.setFuelLevel(.9f);
        builder.setRange(265);
        builder.setCurrentFuelConsumption(8.75f);
        builder.setTripFuelConsumption(6.2f);
        builder.setWasherFluidLevel(WasherFluidLevel.FULL);

        builder.addTireState(new TireStateProperty(
                TireStateProperty.Location.FRONT_LEFT, 2.31f, 40f, 746
        ));
        builder.addTireState(new TireStateProperty(
                TireStateProperty.Location.FRONT_RIGHT, 2.31f, 40f, 746
        ));
        builder.addTireState(new TireStateProperty(
                TireStateProperty.Location.REAR_RIGHT, 2.31f, 40f, 746
        ));
        builder.addTireState(new TireStateProperty(
                TireStateProperty.Location.REAR_LEFT, 2.31f, 40f, 746
        ));

        builder.setBatteryVoltage(12f);
        builder.setAdBlueLevel(.5f);
        builder.setDistanceDrivenSinceReset(1500);
        builder.setDistanceDrivenSinceEngineStart(10);
        builder.setFuelVolume(35.5f);

        byte[] bytes = builder.build().getBytes();
        assertTrue(Arrays.equals(bytes, Bytes.bytesFromHex("0033010100030249F00200020063030002003C04000209C40500015A0600020109070004410c000008000440c66666090001010A000B004013d70a4220000002EA0A000B014013d70a4220000002EA0A000B024013d70a4220000002EA0A000B034013d70a4220000002EA0B0004414000000C00043F0000000D000205DC0E0002000A0F0004420E0000")));
    }

    @Test public void state0Properties() {
        byte[] bytes = Bytes.bytesFromHex("003301");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((DiagnosticsState)state).getTripFuelConsumption() == null);
    }
}
