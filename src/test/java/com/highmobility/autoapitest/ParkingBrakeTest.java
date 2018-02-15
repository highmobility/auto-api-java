package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateInactivateParkingBrake;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
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



        Command command = CommandResolver.resolve(bytes);

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
        String waitingForBytes = "00580200";
        String commandBytes = Bytes.hexFromBytes(new ActivateInactivateParkingBrake(false).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void build() {
        byte[] expectedBytes = Bytes.bytesFromHex(
                "00580101000101");

        ParkingBrakeState.Builder builder = new ParkingBrakeState.Builder();
        builder.setIsActive(true);
        byte[] actualBytes = builder.build().getBytes();
        assertTrue(Arrays.equals(actualBytes, expectedBytes));
    }
}