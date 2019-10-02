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

        assertTrue(command.getClass() == ParkingBrakeState.class);
        ParkingBrakeState state = (ParkingBrakeState) command;
        testState(state);
    }

    private void testState(ParkingBrakeState state) {
        assertTrue(state.getStatus().getValue() == ActiveState.ACTIVE);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "005800";
        String commandBytes = ByteUtils.hexFromBytes(new GetParkingBrakeState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void setParkingBrake() {
        Bytes waitingForBytes = new Bytes("005801" +
                "01000401000101");
        Bytes commandBytes = new SetParkingBrake(ActiveState.ACTIVE);
        assertTrue(TestUtils.bytesTheSame(waitingForBytes, commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        SetParkingBrake command = (SetParkingBrake) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getStatus().getValue() == ActiveState.ACTIVE);
        assertTrue(command.equals(waitingForBytes));
    }

    @Test public void build() {
        ParkingBrakeState.Builder builder = new ParkingBrakeState.Builder();
        builder.setStatus(new Property(ActiveState.ACTIVE));
        ParkingBrakeState state = builder.build();
        testState(state);
    }
}