package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class ParkingBrakeTest extends BaseTest {
    Bytes bytes = new Bytes(
            "005801" +
                    "01000401000101");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);

        assertTrue(command.getClass() == ParkingBrake.State.class);
        ParkingBrake.State state = (ParkingBrake.State) command;
        testState(state);
    }

    private void testState(ParkingBrake.State state) {
        assertTrue(state.getStatus().getValue() == ActiveState.ACTIVE);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "005800";
        String commandBytes = ByteUtils.hexFromBytes(new ParkingBrake.GetState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setParkingBrake() {
        Bytes waitingForBytes = new Bytes("005801" +
                "01000401000101");
        Bytes commandBytes = new ParkingBrake.SetParkingBrake(ActiveState.ACTIVE);
        assertTrue(TestUtils.bytesTheSame(waitingForBytes, commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        ParkingBrake.SetParkingBrake command = (ParkingBrake.SetParkingBrake) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getStatus().getValue() == ActiveState.ACTIVE);
        assertTrue(command.equals(waitingForBytes));
    }

    @Test public void build() {
        ParkingBrake.State.Builder builder = new ParkingBrake.State.Builder();
        builder.setStatus(new Property(ActiveState.ACTIVE));
        ParkingBrake.State state = builder.build();
        testState(state);
    }
}