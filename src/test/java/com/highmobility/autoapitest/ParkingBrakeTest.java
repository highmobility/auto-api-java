package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateInactivateParkingBrake;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetParkingBrakeState;
import com.highmobility.autoapi.ParkingBrakeState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

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
        Bytes bytes = new Bytes(
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
        String commandBytes = ByteUtils.hexFromBytes(new GetParkingBrakeState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void inactivate() {
        Bytes waitingForBytes = new Bytes("00580201");
        byte[] commandBytes = new ActivateInactivateParkingBrake(true)
                .getByteArray();
        assertTrue(waitingForBytes.equals(commandBytes));

        ActivateInactivateParkingBrake command = (ActivateInactivateParkingBrake) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.activate() == true);
        assertTrue(command.equals(waitingForBytes));
    }

    @Test public void build() {
        byte[] expectedBytes = ByteUtils.bytesFromHex(
                "00580101000101");

        ParkingBrakeState.Builder builder = new ParkingBrakeState.Builder();
        builder.setIsActive(true);
        byte[] actualBytes = builder.build().getByteArray();
        assertTrue(Arrays.equals(actualBytes, expectedBytes));
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("005801");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((ParkingBrakeState)state).isActive() == null);
    }
}