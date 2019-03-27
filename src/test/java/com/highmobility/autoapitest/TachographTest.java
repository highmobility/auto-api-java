package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetTachographState;
import com.highmobility.autoapi.TachographState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.tachograph.DriverCard;
import com.highmobility.autoapi.value.tachograph.DriverTimeState;
import com.highmobility.autoapi.value.tachograph.DriverWorkingState;
import com.highmobility.autoapi.value.tachograph.VehicleDirection;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TachographTest {
    Bytes bytes = new Bytes
            ("006401" +
                    "0100050100020102" +
                    "0100050100020200" +
                    "0200050100020102" +
                    "0200050100020206" +
                    "0300050100020101" +
                    "0300050100020201" +
                    "04000401000101" +
                    "05000401000100" +
                    "06000401000100" +
                    "0700050100020050"
            );

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        TachographState state = (TachographState) command;
        testState(state);
    }

    private void testState(TachographState state) {
        assertTrue(state.getDriverWorkingState(1).getValue().getWorkingState() == DriverWorkingState.Value.WORKING);
        assertTrue(state.getDriverWorkingState(2).getValue().getWorkingState() == DriverWorkingState.Value.RESTING);

        assertTrue(state.getDriverTimeState(1).getValue().getTimeState() == DriverTimeState.Value
                .FOUR_AND_HALF_HOURS_REACHED
        );
        assertTrue(state.getDriverTimeState(2).getValue().getTimeState() == DriverTimeState.Value
                .SIXTEEN_HOURS_REACHED
        );

        assertTrue(state.getDriverCard(1).getValue().isPresent());
        assertTrue(state.getDriverCard(2).getValue().isPresent());

        assertTrue(state.isVehicleMotionDetected().getValue() == true);
        assertTrue(state.isVehicleOverspeeding().getValue() == false);
        assertTrue(state.getVehicleDirection().getValue() == VehicleDirection.FORWARD);
        assertTrue(state.getVehicleSpeed().getValue() == 80);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        TachographState.Builder builder = new TachographState.Builder();

        builder.addDriverWorkingState(new Property(new DriverWorkingState(1,
                DriverWorkingState.Value.WORKING)));
        builder.addDriverWorkingState(new Property(new DriverWorkingState(2,
                DriverWorkingState.Value.RESTING)));

        builder.addDriverTimeState(new Property(new DriverTimeState(1, DriverTimeState.Value
                .FOUR_AND_HALF_HOURS_REACHED)));
        builder.addDriverTimeState(new Property(new DriverTimeState(2, DriverTimeState.Value
                .SIXTEEN_HOURS_REACHED)));

        builder.addDriverCard(new Property(new DriverCard(1, true)));
        builder.addDriverCard(new Property(new DriverCard(2, true)));

        builder.setVehicleMotionDetected(new Property(true));
        builder.setVehicleOverspeed(new Property(false));
        builder.setVehicleDirection(new Property(VehicleDirection.FORWARD));
        builder.setVehicleSpeed(new Property(80));

        TachographState state = builder.build();
        testState(state);
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("006400");
        byte[] commandBytes = new GetTachographState().getByteArray();

        assertTrue(waitingForBytes.equals(commandBytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof GetTachographState);
    }
}
