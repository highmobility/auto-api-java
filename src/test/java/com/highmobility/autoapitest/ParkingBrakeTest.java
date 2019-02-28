package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetParkingBrakeState;
import com.highmobility.autoapi.ParkingBrakeState;
import com.highmobility.autoapi.SetParkingBrake;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class ParkingBrakeTest {
    Bytes bytes = new Bytes(
            "005801" +
                    "01000401000101");

    @Test
    public void state() {

        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == ParkingBrakeState.class);
        ParkingBrakeState state = (ParkingBrakeState) command;
        testState(state);
    }

    private void testState(ParkingBrakeState state) {
        assertTrue(state.isActive().getValue() == true);
    }

    @Test public void get() {
        String waitingForBytes = "005800";
        String commandBytes = ByteUtils.hexFromBytes(new GetParkingBrakeState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setParkingBrake() {
        Bytes waitingForBytes = new Bytes("005812" +
                "01000401000101");
        Bytes commandBytes = new SetParkingBrake(true);
        assertTrue(TestUtils.bytesTheSame(waitingForBytes, commandBytes));

        SetParkingBrake command = (SetParkingBrake) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.activate() == true);
        assertTrue(command.equals(waitingForBytes));
    }

    @Test public void build() {
        ParkingBrakeState.Builder builder = new ParkingBrakeState.Builder();
        builder.setIsActive(new ObjectProperty<>(true));
        ParkingBrakeState state = builder.build();
        assertTrue(state.equals(bytes));
        testState(state);
    }

    @Test public void state0Properties() {
        Bytes bytes = new Bytes("005801");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((ParkingBrakeState) state).isActive().getValue() == null);
    }
}