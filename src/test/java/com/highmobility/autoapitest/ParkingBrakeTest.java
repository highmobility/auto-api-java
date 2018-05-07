package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateInactivateParkingBrake;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetParkingBrakeState;
import com.highmobility.autoapi.ParkingBrakeState;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class ParkingBrakeTest {
    @Test
    public void state() {
        byte[] bytes = Bytes.bytesFromHex(
                "00580101000101");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.getClass() == ParkingBrakeState.class);
        ParkingBrakeState state = (ParkingBrakeState) command;
        assertTrue(state.isActive() == true);
    }

    @Test public void get() {
        String waitingForBytes = "005800";
        String commandBytes = Bytes.hexFromBytes(new GetParkingBrakeState().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void inactivate() {
        byte[] waitingForBytes = Bytes.bytesFromHex("00580201");
        byte[] commandBytes = new ActivateInactivateParkingBrake(true)
                .getBytes();
        assertTrue(Arrays.equals(waitingForBytes, commandBytes));

        ActivateInactivateParkingBrake command = (ActivateInactivateParkingBrake) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.activate() == true);
        assertTrue(Arrays.equals(command.getBytes(), waitingForBytes));
    }

    @Test public void build() {
        byte[] expectedBytes = Bytes.bytesFromHex(
                "00580101000101");

        ParkingBrakeState.Builder builder = new ParkingBrakeState.Builder();
        builder.setIsActive(true);
        byte[] actualBytes = builder.build().getBytes();
        assertTrue(Arrays.equals(actualBytes, expectedBytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = Bytes.bytesFromHex("005801");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((ParkingBrakeState)state).isActive() == null);
    }
}