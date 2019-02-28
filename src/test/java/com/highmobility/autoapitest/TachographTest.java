package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetTachographState;
import com.highmobility.autoapi.TachographState;
import com.highmobility.autoapi.property.DriverCard;
import com.highmobility.autoapi.property.DriverTimeState;
import com.highmobility.autoapi.property.DriverWorkingState;
import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.ObjectProperty;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

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

        assertTrue(command.is(TachographState.TYPE));
        TachographState state = (TachographState) command;

        assertTrue(state.getDriverWorkingState(1).getWorkingState() == DriverWorkingState
                .WorkingState.WORKING);
        assertTrue(state.getDriverWorkingState(2).getWorkingState() == DriverWorkingState
                .WorkingState.RESTING);

        assertTrue(state.getDriverTimeState(1).getTimeState() == DriverTimeState.TimeState
                .FOUR_AND_HALF_HOURS_REACHED
        );
        assertTrue(state.getDriverTimeState(2).getTimeState() == DriverTimeState.TimeState
                .SIXTEEN_HOURS_REACHED
        );

        assertTrue(state.getDriverCard(1).isPresent());
        assertTrue(state.getDriverCard(2).isPresent());

        assertTrue(state.isVehicleMotionDetected().getValue() == true);
        assertTrue(state.isVehicleOverspeeding().getValue() == false);
        assertTrue(state.getVehicleDirection() == TachographState.VehicleDirection.FORWARD);
        assertTrue(state.getVehicleSpeed().getValue() == 80);
    }

    @Test public void build() {
        TachographState.Builder builder = new TachographState.Builder();

        builder.addDriverWorkingState(new DriverWorkingState(1, DriverWorkingState.WorkingState
                .WORKING));
        builder.addDriverWorkingState(new DriverWorkingState(2, DriverWorkingState.WorkingState
                .RESTING));

        builder.addDriverTimeState(new DriverTimeState(1, DriverTimeState.TimeState
                .FOUR_AND_HALF_HOURS_REACHED));
        builder.addDriverTimeState(new DriverTimeState(2, DriverTimeState.TimeState
                .SIXTEEN_HOURS_REACHED));

        builder.addDriverCard(new DriverCard(1, true));
        builder.addDriverCard(new DriverCard(2, true));

        builder.setVehicleMotionDetected(new ObjectProperty<>(true));
        builder.setVehicleOverspeed(new ObjectProperty<>(false));
        builder.setVehicleDirection(TachographState.VehicleDirection.FORWARD);
        builder.setVehicleSpeed(new ObjectPropertyInteger(80));

        TachographState state = builder.build();
        assertTrue(state.equals(bytes));
    }

    @Test public void get() {
        Bytes waitingForBytes = new Bytes("006400");
        byte[] commandBytes = new GetTachographState().getByteArray();

        assertTrue(waitingForBytes.equals(commandBytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof GetTachographState);
    }
}
